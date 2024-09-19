/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package akid_convertor;

import javax.swing.JOptionPane;

/**
 *
 * @author Imalka_c
 */
public class AKIDConvertor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //new Loader().setVisible(true);
        Loader load = new Loader();
        load.setVisible(true); 
        try{
             for(int i = 0; i <= 100;i++){
                Thread.sleep(60);
                load.loader.setValue(i);
                if(i == 100){
                    load.dispose();
                    new Login().setVisible(true);
                }
             }
        }catch(Exception e){
            JOptionPane.showMessageDialog(load, e, "Error Occored", JOptionPane.WARNING_MESSAGE);
        }
    }
    
}
