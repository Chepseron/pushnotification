/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;

import com.amon.db.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import org.primefaces.model.chart.MeterGaugeChartModel;

@SessionScoped
@ManagedBean(name = "push")

public class push implements Serializable {

    @PersistenceContext(name = "acc_push_war_1PU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private List<Usergroup> usergroupList = new ArrayList<>();
    private Usergroup usergroup = new Usergroup();
    private List<User> userList = new ArrayList<>();
    private User user = new User();
    private List<Status> statusList = new ArrayList<>();
    private Status status = new Status();
    private List<Audit> auditList = new ArrayList<>();
    private Audit audit = new Audit();
    private List<Endpoint> endpointList = new ArrayList<>();
    private Endpoint endpoint = new Endpoint();
    private List<Outlet> outletList = new ArrayList<>();
    private Outlet outlet = new Outlet();
    private List<Transactions> transactionsList = new ArrayList<>();
    private Transactions transactions = new Transactions();
    private String username = new String();
    private String password = new String();
    private boolean remember = false;
    private boolean admin = false;
    private boolean sales = false;
    private boolean accountant = false;
    private Date date;
    private MeterGaugeChartModel meterGaugeModel1;
    private MeterGaugeChartModel meterGaugeModel2;

    public push() {
    }

    @PostConstruct
    public void init() {
        try {
            createMeterGaugeModels();

        } catch (Exception e) {
        }
    }

    private MeterGaugeChartModel initMeterGaugeModel() {
        List<Number> intervals = new ArrayList<Number>() {
            {
                add(20);
                add(50);
                add(120);
                add(220);
            }
        };
        return new MeterGaugeChartModel(140, intervals);
    }

    private void createMeterGaugeModels() {
        meterGaugeModel1 = initMeterGaugeModel();
        meterGaugeModel1.setTitle("MeterGauge Chart");
        meterGaugeModel1.setGaugeLabel("km/h");
        meterGaugeModel1.setGaugeLabelPosition("bottom");

        meterGaugeModel2 = initMeterGaugeModel();
        meterGaugeModel2.setTitle("Custom Options");
        meterGaugeModel2.setSeriesColors("66cc66,93b75f,E7E658,cc6666");
        meterGaugeModel2.setGaugeLabel("km/h");
        meterGaugeModel2.setGaugeLabelPosition("bottom");
        meterGaugeModel2.setShowTickLabels(false);
        meterGaugeModel2.setLabelHeightAdjust(110);
        meterGaugeModel2.setIntervalOuterRadius(100);
    }

    public String login() {
        try {
            user = (User) em.createQuery("select u from User u where u.username = '" + username + "' and u.pword = '" + password + "'").getSingleResult();

            getUtx().begin();
            getAudit().setAction("logged into the system at  " + new Date());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getUtx().commit();

            return "index2.xhtml?faces-redirect=true";

        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            System.out.println("Username " + username + " password " + password);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please provide correct credentials");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("msgs1", message);
            e.printStackTrace();
            return null;
        }

    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getAttributes().clear();
        return "/index.xhtml?faces-redirect=true";
    }

    public String createOutlet() {
        try {

            getUtx().begin();
            getAudit().setAction("saved outlet " + getOutlet().getOutletname());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getOutlet());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getOutlet().getOutletname() + " saved successfully."));
            setOutlet(new Outlet());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setOutlet(new Outlet());
        return null;
    }

    public String updateOutlet() {
        try {

            Outlet outlet = getEm().find(Outlet.class, this.getOutlet().getIdoutlet());

            getUtx().begin();
            getAudit().setAction("updated outlet " + outlet.getOutletname());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(outlet);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", outlet.getOutletname() + " Updated successfully."));
            setOutlet(new Outlet());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setOutlet(new Outlet());
        return null;
    }

    public String deleteOutlet(Outlet outlet) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted Outlet");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Outlet toBeRemoved = (Outlet) getEm().merge(outlet);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            outlet = new Outlet();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Outlet deleted", "Outlet deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Outlet", success);

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Outlet", success);
        }
        setOutlet(new Outlet());
        return null;
    }

    public String createTransaction() {
        try {

            getUtx().begin();
            getAudit().setAction("saved transaction " + getTransactions().getAmount());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getTransactions());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getTransactions().getAmount() + " saved successfully."));
            setTransactions(new Transactions());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
            ex.printStackTrace();
        }
        setTransactions(new Transactions());
        return null;
    }

    public String updateTransaction() {
        try {
            Transactions trans = getEm().find(Transactions.class, transactions.getIdtransactions());
            getUtx().begin();
            getAudit().setAction("updated transactions " + trans.getAmount());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(trans);
            getUtx().commit();
            String response = sendGet(transactions.getMpesastatement().replaceAll(" ", "%20"));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getTransactions().getAmount() + response));
            setTransactions(new Transactions());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
            ex.printStackTrace();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));

            Logger.getLogger(push.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        setTransactions(new Transactions());
        return null;
    }

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private String sendGet(String stmt) throws Exception {
        endpoint = (Endpoint) em.createQuery("select e from Endpoint e where e.endpointName = 'PRINTER'").getSingleResult();
        String ip = endpoint.getUrltoendpoint();
        startConnection(ip, 11000);
        String response = sendMessage(stmt);
        return response;

    }

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception ex) {
            Logger.getLogger(push.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public String sendMessage(String msg) {
        try {
            out.println(msg);
            String resp = in.readLine();
            return resp;
        } catch (Exception ex) {
            Logger.getLogger(push.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception ex) {
            Logger.getLogger(push.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String deleteTransactions(Transactions transactions) {
        try {
            getUtx().begin();
            getAudit().setAction("Deleted transaction");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Transactions toBeRemoved = (Transactions) getEm().merge(transactions);
            getEm().remove(toBeRemoved);
            getUtx().commit();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Transaction deleted", "Transaction deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Transaction", success);
            transactions = new Transactions();
            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Transaction", success);
        }
        transactions = new Transactions();
        return null;
    }

    public String createUsergroup() {

        try {
            getUtx().begin();
            getAudit().setAction("created group");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getUsergroup().setCreatedBy(new User(1));
            getUsergroup().setCreatedAt(new java.util.Date());
            getUsergroup().setResponsibilities("ALL");
            getUsergroup().setStatusID(new Status(1));
            getEm().persist(getAudit());
            getEm().persist(getUsergroup());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getUsergroup().getName() + " saved successfully."));

        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            Logger.getLogger(push.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", getUsergroup().getName() + " could not create Usergroup successfully."));
            ex.printStackTrace();
        }
        setUsergroup(new Usergroup());
        return null;
    }

    public String updateUsergroup() {
        try {

            Usergroup group2 = getEm().find(Usergroup.class, getUsergroup().getIdgroups());
            getUtx().begin();
            getAudit().setAction("updated group");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(group2);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", group2.getName() + " Updated successfully."));
            group2 = new Usergroup();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a group."));
        }
        setGroup1(new Usergroup());
        return null;
    }

    public String deleteUsergroup(Usergroup group) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted group");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Usergroup toBeRemoved = (Usergroup) getEm().merge(group);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            group = new Usergroup();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", group.getName() + " Deleted successfully."));

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", group.getName() + " failed to delete successfully."));
        }
        group = new Usergroup();
        return null;
    }

    public String createEndpoint() {

        try {
            getUtx().begin();
            getAudit().setAction("created endpoint");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getEndpoint());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getEndpoint().getEndpointName() + " saved successfully."));

        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            Logger.getLogger(push.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", getEndpoint().getEndpointName() + " could not create Endpoint successfully."));
            ex.printStackTrace();
        }
        setEndpoint(new Endpoint());
        return null;
    }

    public String updateEndpoint() {
        try {

            Endpoint endpoint2 = getEm().find(Endpoint.class, getEndpoint().getIdendpoint());
            getUtx().begin();
            getAudit().setAction("updated endpoint");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(endpoint2);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", endpoint2.getEndpointName() + " Updated successfully."));
            endpoint2 = new Endpoint();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a endpoint."));
        }
        setEndpoint(new Endpoint());
        return null;
    }

    public String deleteEndpoint(Endpoint endpoint) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted endpoint");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Endpoint toBeRemoved = (Endpoint) getEm().merge(endpoint);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            endpoint = new Endpoint();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", endpoint.getEndpointName() + " Deleted successfully."));

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", endpoint.getEndpointName() + " failed to delete successfully."));
        }
        endpoint = new Endpoint();
        return null;
    }

    public String createUser() {
        try {

            getUser().setCreatedAt(new java.util.Date());
            getUser().setCreatedBy(1);
            getUser().setLastLogin(new java.util.Date());
            getUser().setStatusID(1);
            getUser().setPword("1234");
            getUser().setDepartment(1);
            getUtx().begin();
            getAudit().setAction("saved user " + getUser().getUsername());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getUser());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getUser().getName() + " saved successfully."));
            setUser(new User());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
            ex.printStackTrace();
        }
        setUser(new User());
        return null;
    }

    public String updateUser() {
        try {

            User user2 = getEm().find(User.class,
                    getUser().getIdusers());
            user2.setCreatedAt(new java.util.Date());
            user2.setCreatedBy(1);
            user2.setDepartment(1);
            user2.setEmail(getUser().getEmail());
            user2.setGroupID(getUser().getGroupID());
            user2.setLastLogin(new java.util.Date());
            user2.setName(getUser().getName());
            user2.setPhone(getUser().getPhone());
            user2.setPword("1234");
            user2.setStaffID(getUser().getStaffID());
            user2.setStatusID(1);
            user2.setUsername(getUser().getUsername());

            getUtx().begin();
            getAudit().setAction("updated user " + getUser().getIdusers());
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(user2);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getUser().getName() + " Updated successfully."));
            setUser(new User());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setUser(new User());
        return null;
    }

    public String deleteUser(User user) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted user");
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            User toBeRemoved = (User) getEm().merge(user);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            user = new User();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User deleted", "User deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("User", success);

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("User", success);
        }
        user = new User();
        return null;
    }

    public String createStatus() {
        try {

            getStatus().setCreatedBy(getUser().getIdusers());
            getUtx().begin();
            getAudit().setAction("created status");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getStatus());
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getStatus().getName() + " saved successfully."));
            setStatus(new Status());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setStatus(new Status());
        return null;
    }

    public String updateStatus() {
        try {

            Status statu = getEm().find(Status.class, this.getStatus().getIdstatus());

            statu.setCreatedBy(getUser().getIdusers());
            statu.setDescription(getStatus().getDescription());
            statu.setName(getStatus().getName());

            getUtx().begin();
            getAudit().setAction("updated status");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());

            getEm().persist(getAudit());
            getEm().merge(statu);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getStatus().getName() + " Updated successfully."));
            setStatus(new Status());
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a status."));
        }
        setStatus(new Status());
        return null;
    }

    public String deleteStatus(Status status) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted status");
            getAudit().setCreatedby(getUser().getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Status toBeRemoved = (Status) getEm().merge(status);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            status = new Status();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Status deleted", "Status deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);

            return null;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);
        }
        status = new Status();
        return null;
    }

    public List<Usergroup> getUsergroupList() {
        usergroupList = getEm().createQuery("select u from Usergroup u").getResultList();
        return usergroupList;
    }

    /**
     * @param usergroupList the usergroupList to set
     */
    public void setUsergroupList(List<Usergroup> usergroupList) {
        this.setUsergroupList(usergroupList);
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the userList
     */
    public List<User> getUserList() {
        userList = getEm().createQuery("select u from User u").getResultList();
        return userList;
    }

    /**
     * @param userList the userList to set
     */
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the statusList
     */
    public List<Status> getStatusList() {
        statusList = getEm().createQuery("select s from Status s").getResultList();
        return statusList;
    }

    /**
     * @param statusList the statusList to set
     */
    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    /**
     * @return the audit
     */
    public Audit getAudit() {
        return audit;
    }

    /**
     * @param audit the audit to set
     */
    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    /**
     * @return the auditList
     */
    public List<Audit> getAuditList() {
        auditList = getEm().createQuery("select a from Audit a").getResultList();
        return auditList;
    }

    /**
     * @param auditList the auditList to set
     */
    public void setAuditList(List<Audit> auditList) {
        this.auditList = auditList;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGroup1List(List<Usergroup> usergroupList) {
        this.setUsergroupList(usergroupList);
    }

    /**
     * @param em the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * @return the utx
     */
    public UserTransaction getUtx() {
        return utx;
    }

    /**
     * @param utx the utx to set
     */
    public void setUtx(UserTransaction utx) {
        this.utx = utx;
    }

    public Usergroup getUsergroup() {
        return usergroup;
    }

    /**
     * @param usergroup thgetEm()usergroup to set
     */
    public void setGroup1(Usergroup usergroup) {
        this.setUsergroup(usergroup);
    }

    /**
     * @return the outletList
     */
    public List<Outlet> getOutletList() {
        outletList = getEm().createQuery("select o from Outlet o").getResultList();
        return outletList;
    }

    /**
     * @param outletList the outletList to set
     */
    public void setOutletList(List<Outlet> outletList) {
        this.outletList = outletList;
    }

    /**
     * @return the outlet
     */
    public Outlet getOutlet() {
        return outlet;
    }

    /**
     * @param outlet the outlet to set getEm()
     */
    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    /**
     * @return the transactionsList
     */
    public List<Transactions> getTransactionsList() {
        transactionsList = getEm().createQuery("select t from Transactions t").getResultList();
        return transactionsList;
    }

    /**
     * @param transactionsList the transactionsList to set
     */
    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public Transactions getTransactions() {
        return transactions;
    }

    /**
     * @param transactions the transactions to set
     */
    public void setTransactions(Transactions transactions) {
        this.transactions = transactions;
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @return the remember
     */
    public boolean isRemember() {
        return remember;
    }

    /**
     * @param remember the remember to set
     */
    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public void setUsergroup(Usergroup usergroup) {
        this.usergroup = usergroup;
    }

    /**
     * @return the admin
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * @return the sales
     */
    public boolean isSales() {
        return sales;
    }

    /**
     * @param sales the sales to set
     */
    public void setSales(boolean sales) {
        this.sales = sales;
    }

    /**
     * @return the accountant
     */
    public boolean isAccountant() {
        return accountant;
    }

    /**
     * @param accountant the accountant to set
     */
    public void setAccountant(boolean accountant) {
        this.accountant = accountant;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        date = new java.util.Date();
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the meterGaugeModel1
     */
    public MeterGaugeChartModel getMeterGaugeModel1() {
        return meterGaugeModel1;
    }

    /**
     * @param meterGaugeModel1 the meterGaugeModel1 to set
     */
    public void setMeterGaugeModel1(MeterGaugeChartModel meterGaugeModel1) {
        this.meterGaugeModel1 = meterGaugeModel1;
    }

    /**
     * @return the meterGaugeModel2
     */
    public MeterGaugeChartModel getMeterGaugeModel2() {
        return meterGaugeModel2;
    }

    /**
     * @param meterGaugeModel2 the meterGaugeModel2 to set
     */
    public void setMeterGaugeModel2(MeterGaugeChartModel meterGaugeModel2) {
        this.meterGaugeModel2 = meterGaugeModel2;
    }

    /**
     * @return the endpointList
     */
    public List<Endpoint> getEndpointList() {
        endpointList = em.createQuery("select e from Endpoint e").getResultList();
        return endpointList;
    }

    /**
     * @param endpointList the endpointList to set
     */
    public void setEndpointList(List<Endpoint> endpointList) {
        this.endpointList = endpointList;
    }

    /**
     * @return the endpoint
     */
    public Endpoint getEndpoint() {
        return endpoint;
    }

    /**
     * @param endpoint the endpoint to set
     */
    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

}
