package org.eve.sd.supplier.controller;

import org.eve.main.EVE;
import org.eve.model.Model;
import org.eve.sd.supplier.Supplier;
import org.eve.view.AbstractController;
import org.eve.view.Form;
import org.hibernate.HibernateException;

public class SupplierController extends AbstractController {

    @Override
    public void userInput(String input) {
        String id_;
        Supplier supplier = (Supplier)getObject();
        Form form = getForm("main");
        Model model = getModel();
        
        if (input.equals("supplier.save")) {
            try {
                /*
                 * dados base
                 */
                for (Object id : supplier.getIds()) {
                    id_ = (String)id;
                    if (id_.equals(Supplier.USREG))
                        continue;
                    
                    supplier.setFieldValue(id_, form.getFieldValue(supplier, id_));
                }
                
                model.save(supplier);
                form.setInt("document.ident", supplier.getId());
                form.setDate("document.dtreg", supplier.getRegDate());
                form.setTime("document.tmreg", supplier.getRegTime());
                
                setMessage(EVE.status, "supplier.save.success");
                
                return;
            } catch (HibernateException ex) {
                setMessage(EVE.error, "supplier.save.error");
                ex.printStackTrace();                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

}
