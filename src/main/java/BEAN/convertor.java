package BEAN;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.WeakHashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ViewScoped
@ManagedBean(name = "convertor")
@FacesConverter("convertor")
public class convertor
        implements Converter {

    private static Map<Object, String> entities = new WeakHashMap();

    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        synchronized (entities) {
            if (!entities.containsKey(entity)) {
                String uuid = UUID.randomUUID().toString();
                entities.put(entity, uuid);

                return uuid;
            }
           
            return (String) entities.get(entity);
        }
    }

    public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
        for (Map.Entry<Object, String> entry : entities.entrySet()) {
            if (((String) entry.getValue()).equals(uuid)) {
             
                return entry.getKey();
            }
        }
        return null;
    }
}
