package  com.ai;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.i18n.ResourceBundle;

class ConfigurationsScreen  extends MainScreen implements salesmonitorResource{
    
    //private ResourceBundle resources = ResourceBundle.getBundle(KnowledgeBaseResource.BUNDLE_ID,
     //       KnowledgeBaseResource.BUNDLE_NAME);
            
    private EditField appServerUrlField;
    //private EditField maxCachedArticlesField;
    
    private MenuItem saveMenu = new MenuItem("Salvar Configuración",100,10) {
       public void run() {
           saveOptions();
       } 
    };
    
    ConfigurationsScreen() {    
        
        this.setTitle(options.appName+" Configuración");
        
        appServerUrlField = new EditField("URL. WebService:","",256,EditField.EDITABLE | EditField.FILTER_URL);
        //maxCachedArticlesField = new EditField(resources.getString(KnowledgeBaseResource.LBL_MAX_CACHED_RESULTS),"",2,EditField.EDITABLE | EditField.FILTER_INTEGER);
        
        this.add(appServerUrlField);
        //this.add(maxCachedArticlesField);
    
    }
    
    protected void makeMenu(Menu menu, int instance) {
        menu.add(saveMenu);
        menu.add(MenuItem.separator(saveMenu.getOrdinal() + 1));
        super.makeMenu(menu,instance);
    }
    
    public void displayOptions() {
        
        String serverUrl = DataStore.getAppServerUrl();
        if(serverUrl=="")
        {
    		ResourceBundle _resources = ResourceBundle.getBundle(BUNDLE_ID, BUNDLE_NAME);
    		serverUrl=_resources.getString(URLWS);        	
        }
        appServerUrlField.setText(serverUrl);
        
        
    }
    
    private void saveOptions() {
        
        String serverUrl = appServerUrlField.getText().trim();        
        
        if (null == serverUrl || serverUrl.length() == 0 ) {
              
              Dialog.alert("Configuración Invalida");
              return;  
        }
        
        DataStore.setAppServerUrl(serverUrl);        
        
        // Trim the cached articles list if needed.
        
        
        this.setDirty(false);
        this.close();
        
    }
    
   
    protected boolean onSavePrompt() {
        
        if (Dialog.YES == Dialog.ask(Dialog.D_YES_NO, "Salir sin salvar?")) {
            this.setDirty(false);
            return true;
        }  else {
            return false;
        }     
    }
} 
