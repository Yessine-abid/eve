package org.eve.view;

import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eve.main.EVE;
import org.springframework.context.MessageSource;

public class MessageBar {
    private Group msggrp;
    private Label msgtxt;
    private MessageSource messages;
    private Composite container;
    
    public MessageBar(Composite container) {
        this.container = container;
    }
    
    /*
     * 
     * Setters
     * 
     */
    /**
     * Define fonte de mensagens
     * @param messages
     */
    public final void setMessages(MessageSource messages) {
        this.messages = messages;
    }
    
    /**
     * Ajusta mensagem da barra
     * @param status
     * @param message
     */
    public final void setMessage(int status, String id, Locale locale) {
        String message;
        
        switch (status) {
        case EVE.error:
            msggrp.setText("Erro");
            break;
            
        case EVE.status:
            msggrp.setText("");
            break;
        }
        
        message = messages.getMessage(id, null, locale);
        msgtxt.setText(message);
        msggrp.pack();
        container.pack();
    }
    
    /*
     * 
     * Others
     * 
     */
    
    /**
     * Inicializa barra de mensagens
     * @param container
     */
    public final void init() {
        msggrp = new Group(container, SWT.SHADOW_IN);
        msggrp.setLayout(new RowLayout(SWT.VERTICAL));
        msgtxt = new Label(msggrp, SWT.NONE);
    }
    
    /**
     * Limpa barra de mensagem
     */
    public final void clear() {
        msggrp.setText("");
        msgtxt.setText("");
    }
}
