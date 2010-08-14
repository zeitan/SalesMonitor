package  com.ai;

import net.rim.device.api.system.*;
import net.rim.device.api.collection.util.*;
import net.rim.device.api.i18n.ResourceBundle;

class DataStore implements salesmonitorResource {
   
   private static PersistentObject store;
   private static DataStore instance;
   private LongHashtableCollection  settings;
   
   private static final long KEY_APP_SRV_URL = 0;   
   private static ResourceBundle _resources = ResourceBundle.getBundle(BUNDLE_ID, BUNDLE_NAME);
        
   
   private static String DEFAULT_SERVICE_URL = _resources.getString(URLWS);

   
   private static DataStore getInstance() {
        if (null == instance) {
            instance = new DataStore();
        }
        return instance; 
    }
    
    private DataStore() {
        store = PersistentStore.getPersistentObject(0xf6354ee14c6cc857L);
    }
    
    public static String getAppServerUrl() {
       String url = (String)getInstance().get(KEY_APP_SRV_URL);
       if (null == url || url.length() == 0) {
         url =  DEFAULT_SERVICE_URL; 
         setAppServerUrl(url);
       }
       return url;
    }
   
    public static void setAppServerUrl(String url) {
       getInstance().set(KEY_APP_SRV_URL,url);
    } 
    




    
    private void set(long key, Object value) {
        synchronized(store) {
            settings = (LongHashtableCollection)store.getContents();
            if (null == settings) {
                settings = new LongHashtableCollection();
            }
            settings.put(key,value);   
            store.setContents(settings);
            store.commit();
        }
    }    
    
    private Object get(long key) {
        synchronized(store) {
            settings = (LongHashtableCollection)store.getContents();
            if (null != settings && settings.size() != 0) {
                 return settings.get(key);
            } else {
                 return null;
            }
        }
    }
   
} 
