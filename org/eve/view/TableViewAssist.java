package org.eve.view;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * Assistente de tabela
 * @author francisco.prates
 *
 */
public class TableViewAssist extends AbstractTableAssist {
    private ComboAssist comboassist;
    private Composite area;
    private Composite btarea;
    private Map<String, Button> buttons;
    
    public TableViewAssist(String name) {
        super(name);
        comboassist = new ComboAssist();
        comboassist.setType(ComponentType.CCOMBO);
        buttons = new HashMap<String, Button>();
    }
    
    @Override
    protected final void setComponentState(String name, boolean state) {
        buttons.get(name).setEnabled(state);
    }
    
    /*
     * 
     * Others
     * 
     */
    
    /**
     * 
     * @param controller
     * @param visible
     * @param tag
     * @param action
     */
    private void defButton(Controller controller, String tag, String action) {
        Button bt = new Button(btarea, SWT.NONE);
        
        buttons.put(action, bt);
        insertActionState(action);
        setActionState(action, getEditable());
        
        bt.setText(getMessage(tag));
        bt.addSelectionListener(controller);
        controller.putWidget(bt, action);
    }
    
    /**
     * Constrói tabela
     * @param container
     * @param listener
     * @return
     */
    @Override
    public final Composite define(Composite container) {
        int lines;
        TableColumn tablecol;
        TableComponent component;
        Label title;
        Table table;
        Controller controller = getController();
        String name = getName();
        
        
        area = new Composite(container, SWT.NONE);
        area.setLayout(new GridLayout(1, false));
        
        if (name != null) {
            btarea = new Composite(area, SWT.NONE);
            btarea.setLayout(new RowLayout(SWT.HORIZONTAL));
            btarea.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
            btarea.setVisible(getEditable());
            
            defButton(controller, "tableline.insert", name+".insert");
            defButton(controller, "tableline.remove", name+".remove");
            defButton(controller, "tableline.update", name+".update");
            
            btarea.pack();
            
            title = new Label(area, SWT.NONE);
            title.setText(getMessage(name));
        }
        
        table = new Table(area, SWT.BORDER);
        setTable(table);
        
        table.setHeaderVisible(true);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        comboassist.setContainer(table);
        comboassist.setController(getController());
        
        for (Component component_ : getComponents()) {
            component = (TableComponent)component_;
            tablecol = new TableColumn(table, SWT.NONE);
            tablecol.setText(component.getName());
            tablecol.pack();
            component.setColumn(tablecol);
        }
        
        lines = getLines();
        for (int k = 0; k < lines; k++)
            new TableItem(table, SWT.NONE);
        
        /*
         * por motivos que não conheço, não se recomenda usar TableEditor
         * logo após TableItem. Ocorrem problemas graves de dimensionamento
         * do controle de tabela.
         */
        for (TableItem item : table.getItems())
            addTableItem(item);
        
        return area;
    }
}
