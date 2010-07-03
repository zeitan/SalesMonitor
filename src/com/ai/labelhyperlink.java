package com.ai;
import net.rim.device.api.ui.ContextMenu;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;

/**
 * 
 * @author chad.lafontaine
 *
 */
public class labelhyperlink extends LabelField {

    private int pageCount = -1;
    private MenuItem mGetLinkMenuItem;
    /**
     * 
     * @param hyperlinkLabel - label to display link
     */    
    public labelhyperlink(String hyperlinkLabel, int pageCount) {
        super(hyperlinkLabel, FOCUSABLE);
        this.pageCount = pageCount;
        Font font = getBasefontSize(10);
        setFont(font.derive(Font.UNDERLINED | Font.PLAIN));
        mGetLinkMenuItem = new SampleOpenScreenMenuItem(hyperlinkLabel);
    }

    /**
     * Context menu
     */
    public ContextMenu getContextMenu() {
        // Add our "Get Link" menu item to the context menu
        ContextMenu menu = super.getContextMenu();
        menu.addItem(mGetLinkMenuItem);
        return menu;
    }

    /**
     * Inner class 
     */
    class SampleOpenScreenMenuItem extends MenuItem {

        public SampleOpenScreenMenuItem(String menuLabel) {
            super(menuLabel, 0, 100);
        }

        public void run() {
            UiApplication.getUiApplication().pushScreen(new salesdetailsController(pageCount));

        }

    }

    /**
     * Base font of the application. Method is static for other UI components to
     * call for calculation of display fields.
     * 
     * @return
     */
    public static Font getBasefontSize(int size) {
        Font baseFont = null;
        try {
            baseFont = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, size);
        } catch (ClassNotFoundException e) {
            baseFont = Font.getDefault().getFontFamily().getFont(FontFamily.SCALABLE_FONT, size);
        }

        return baseFont;
    }
}
