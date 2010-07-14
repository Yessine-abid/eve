package org.eve.main;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eve.view.Controller;
import org.eve.view.View;
import org.eve.view.ViewAction;

public class EveApp implements EveAPI {
    private final static String VERSION = "1.9.6";
    private List<Controller> controllers;
    private Map<View, Controller> controlmap;
    private Map<String, View> viewmap;
    private GeneralListener listener;
    private Composite container;
    private Tree selector;
    
    public EveApp() {
        viewmap = new HashMap<String, View>();
        controlmap = new HashMap<View, Controller>();
    }
    
    /*
     * 
     * Setters
     * 
     */
    
    public final void setListener(GeneralListener listener) {
        this.listener = listener;
        this.listener.setSystem(this);
    }
    
    /**
     * 
     * @param controllers
     */
    public final void setControllers(List<Controller> controllers) {
        Map<String, View> views;
        
        this.controllers = controllers;
        
        controlmap.clear();
        for(Controller controller : controllers) {
            views = controller.getViews();
            for(String viewname: views.keySet())
                controlmap.put(views.get(viewname), controller);
        }
        
    }
    
    /**
     * 
     * @param container
     */
    public final void setContainer(Composite container) {
        this.container = container;
    }
    
    /**
     * 
     * @param selector
     */
    public final void setSelector(Tree selector) {
        this.selector = selector;
    }
    
    /*
     * 
     * Getters
     * 
     */
    
    public final List<Controller> getControllers() {
        return controllers;
    }
    
    /**
     * Retorna o listener da árvore de opções
     * @return
     */
    public final GeneralListener getListener() {
        return listener;
    }
    
    /**
     * Retorna versão do sistema
     * @return
     */
    public final String getVersion() {
        return VERSION;
    }
    
    /*
     * 
     * Services
     * 
     */
    
    @Override
    public final void call(String action) {
        StackLayout layout;
        Controller controller;
        View view = viewmap.get(action);
        
        if (view == null)
            return;
        
        controller = controlmap.get(view);
        controller.setView(view);
        controller.getMessageBar().clear();
        view.reload(action);
        
        layout = (StackLayout)container.getLayout();
        layout.topControl = view.getContainer();
        container.layout();
        container.pack();
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.main.EveAPI#getController(org.eve.view.View)
     */
    @Override
    public final Controller getController(View view) {
        return controlmap.get(view);
    }
    
    /**
     * Adiciona visões ao container principal
     * @param lviews
     * @param listener
     */
    public void addControllers(List<Controller> lcontrollers, GeneralListener listener) {
        TreeItem item;
        TreeItem subitem;
        Composite container;
        View view;
        Map<String, View> views;
        Map<String, TreeItem> tree = new HashMap<String, TreeItem>();
        
        /*
         * assemblies main tree.
         */
        for (Controller controller : lcontrollers) {
            controller.setLocale(Locale.getDefault());
            controller.setSystem(this);

            views = controller.getViews();
            for (String viewname : views.keySet()) {
                container = new Composite(this.container, SWT.NONE);
                
                view = views.get(viewname);
                view.setSystem(this);
                view.setMessages(controller.getMessages());
                view.buildView(container);
                
                controller.setView(view);
                controller.initMsgBar(container);
                
                if (tree.containsKey(view.getName())) {
                    item = tree.get(view.getName());
                } else {
                    item = new TreeItem(selector, SWT.NONE);
                    item.setText(view.getName());
                    tree.put(view.getName(), item);
                }
                
                for (ViewAction action : view.getActions()) {
                    viewmap.put(action.getId(), view);
                    
                    if (!action.isVisible())
                        continue;
                    
                    subitem = new TreeItem(item, SWT.NONE);
                    subitem.setText(action.getText());
                    listener.putSelectorItem(subitem, action.getId());
                }
            }
        }        
    }
}
