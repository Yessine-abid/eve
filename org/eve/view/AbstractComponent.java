package org.eve.view;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eve.main.EVE;

public abstract class AbstractComponent implements Component {
    private String name;
    private int length;
    private int type;
    private boolean nocase;
    private boolean enabled;
    private Locale locale;
    private Map<Object, String> options;
    private Control control;
    private List<ControlEditor> editors;
    private DateFormat dateformat;

    public AbstractComponent() {
        nocase = false;
        enabled = true;
        editors = new ArrayList<ControlEditor>();
    }
    
    private final String getText(Control control) {
        String value;
        
        switch (type) {
        case EVE.ccombo:
            value = ((CCombo)control).getText();
            if (value == null)
                return "";
            
            if (!nocase)
                ((CCombo)control).setText(value.toUpperCase(locale));
            
            return value;
            
        case EVE.combo:
            value = ((Combo)control).getText();
            if (value == null)
                return "";
            
            if (!nocase)
                ((Combo)control).setText(value.toUpperCase(locale));
            
            return value;
            
        case EVE.text:
            value = ((Text)control).getText();
            if (value == null)
                return "";
            
            if (!nocase)
                ((Text)control).setText(value.toUpperCase(locale));
            
            return value;
            
        default:
            return "";
        }
        
    }

    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#getControl()
     */
    @Override
    public final Control getControl() {
        return control;
    }

    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#getControl(int)
     */
    @Override
    public final Control getControl(int index) {
        return editors.get(index).getEditor();
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#getFloat()
     */
    @Override
    public final float getFloat() {
        String test = getString();
        
        return test.equals("")? 0:Float.parseFloat(test);        
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#getInt()
     */
    @Override
    public final int getInt() {
        String test;
        int test_;
        
        switch (type) {
        case EVE.text:
            test = getString();
            return test.equals("")? 0:Integer.parseInt(test);
        
        case EVE.combo:
            test_ = ((Combo)control).getSelectionIndex();
            return (test_ < 0)? 0:test_;
            
        default:
            return 0;
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#getLength()
     */
    @Override
    public final int getLength() {
        return length;
    }

    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#getName()
     */
    @Override
    public final String getName() {
        return name;
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#getOptions()
     */
    @Override
    public final Map<Object, String> getOptions() {
        return options;
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#getOption(java.lang.Object)
     */
    @Override
    public final String getOption(Object object) {
        return options.get(object);
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#getString()
     */
    @Override
    public final String getString() {
        return getText(control);
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#getStringValue(int)
     */
    @Override
    public final String getString(int index) {
        return getText(editors.get(index).getEditor());
    }

    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#getType()
     */
    @Override
    public final int getType() {
        return type;
    }

    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#isEnabled()
     */
    @Override
    public final boolean isEnabled() {
        return enabled;
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#isNocase()
     */
    @Override
    public final boolean isNocase() {
        return nocase;
    }
    
    /**
     * 
     * @param control
     * @param text
     */
    private final void setText(Control control, String text) {
        String text_ = text;
        
        if (text_ == null)
            text_ = "";        
        
        if (!nocase)
            text_ = text_.toUpperCase(locale);
        
        switch (type) {
        case EVE.text:
            ((Text)control).setText(text_);
            break;
        
        case EVE.ccombo:
            ((CCombo)control).setText(text_);
            break;
            
        case EVE.combo:
            ((Combo)control).setText(text_);
            break;
        }        
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setControl(org.eclipse.swt.widgets.Control)
     */
    @Override
    public final void setControl(Control control) {
        this.control = control;
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setDate(java.util.Date)
     */
    @Override
    public final void setDate(Date date) {
        if (date == null)
            setString("");
        else 
            setString(dateformat.format(date));
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setEnabled(boolean)
     */
    @Override
    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setFloat(float)
     */
    @Override
    public final void setFloat(float value) {
        setString(Float.toString(value));        
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setInt(int)
     */
    @Override
    public final void setInt(int value) {        
        switch (type) {
        case EVE.text:
            setString(Integer.toString(value));
            break;
        
        case EVE.ccombo:
        case EVE.combo:
            if (options == null)
                break;
            
            setString(options.get(value));
            break;
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setInt(int, int)
     */
    @Override
    public final void setInt(int value, int index) {
        switch (type) {
        case EVE.text:
            setString(Integer.toString(value), index);
            break;
        
        case EVE.ccombo:
        case EVE.combo:
            if (options == null)
                break;
            
            setString((String)options.values().toArray()[value], index);
            break;
        }
        
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setLength(int)
     */
    @Override
    public final void setLength(int length) {
        this.length = length;
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setLocale(java.util.Locale)
     */
    @Override
    public final void setLocale(Locale locale) {
        this.locale = locale;
        dateformat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setName(java.lang.String)
     */
    @Override
    public final void setName(String name) {
        this.name = name;
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setNocase(boolean)
     */
    @Override
    public final void setNocase(boolean nocase) {
        this.nocase = nocase;
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setOptions(java.lang.String[])
     */
    @Override
    public final void setOptions(String[] options) {
        if (this.options == null)
            this.options = new LinkedHashMap<Object, String>();
        else
            this.options.clear();
        
        if (options == null)
            return;
        
        for (int k = 0; k < options.length; k++)
            this.options.put(k, options[k]);            
    }
    
    @Override
    public final void setOptions(Map<Object, String> options) {
        this.options = options;
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setString(java.lang.String)
     */
    @Override
    public final void setString(String text) {
        setText(control, text);
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setString(java.lang.String, int)
     */
    @Override
    public final void setString(String text, int index) {
        setText(editors.get(index).getEditor(), text);
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setTime(java.sql.Time)
     */
    @Override
    public final void setTime(Time time) {
        String value;
        
        if (time == null) {
            setString("");
        } else {
            value = time.toString();
            setString(value.equals("00:00:00")?"":value);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setTime(java.sql.Time, int)
     */
    @Override
    public final void setTime(Time time, int index) {
        String value;
        
        if (time == null) {
            setString("", index);
        } else {
            value = time.toString();
            setString(value.equals("00:00:00")?"":value, index);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#setType(int)
     */
    @Override
    public final void setType(int type) {
        this.type = type;
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#addEditor(org.eclipse.swt.custom.ControlEditor)
     */
    @Override
    public final void addEditor(ControlEditor editor) {
        editors.add(editor);
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#clear()
     */
    @Override
    public final void clear() {
        for (ControlEditor editor : editors) {
            editor.getEditor().dispose();
            editor.dispose();
        }
        
        editors.clear();
        
        if (control == null)
            return;
        
        switch (type) {
        case EVE.text:
            ((Text)control).setText("");
            break;
            
        case EVE.combo:
            ((Combo)control).setText("");
            break;
            
        case EVE.ccombo:
            ((CCombo)control).setText("");
            break;
        }        
    }
    
    /*
     * (non-Javadoc)
     * @see org.eve.view.Component#commit()
     */
    @Override
    public final void commit() {
        CCombo ccombo;
        Combo combo;
        
        for (ControlEditor editor : editors) {
            editor.getEditor().setEnabled(enabled);
            
            switch (type) {
            case EVE.combo:
                combo = (Combo)editor.getEditor();
                combo.setEnabled(isEnabled());
                combo.removeAll();
                
                for (String option : options.values())
                    combo.add(option);
                
                break;
                
            case EVE.ccombo:
                ccombo = (CCombo)editor.getEditor();
                ccombo.setEnabled(isEnabled());
                ccombo.removeAll();
                
                for (String option : options.values())
                    ccombo.add(option);
                
                break;
            }
        }
        
        if (control == null)
            return;
        
        switch (type) {
        case EVE.text:
            ((Text)control).setEnabled(isEnabled());
            break;
            
        case EVE.combo:
            combo = (Combo)control;
            combo.setEnabled(isEnabled());
            combo.removeAll();
            
            for (String option : options.values())
                combo.add(option);
            
            break;
            
        case EVE.ccombo:
            ccombo = (CCombo)control;
            ccombo.setEnabled(isEnabled());
            ccombo.removeAll();
            
            for (String option : options.values())
                ccombo.add(option);
            
            break;
        }        
    }
}
