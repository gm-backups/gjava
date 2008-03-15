/*
 * GCppDialog.java
 *
 * Created on 1 de Novembro de 2007, 12:25
 */

package org.gcreator.compilers;

import org.gcreator.core.*;

import java.io.*;

/**
 *
 * @author  Luís
 */
public class GCppDialog extends javax.swing.JFrame implements Runnable{
    
    /** Creates new form GCppDialog */
    int mode;
    String url;
    org.gcreator.fileclass.Project project;
    
    public GCppDialog(org.gcreator.fileclass.Project project) {
        initComponents();
        File y = new File(".gcpp");
        if(y.exists()){
            try{
                FileReader r = new FileReader(y);
                int c = r.read();
                if(c=='G')
                    mode = 0;
                else if(c=='M'){
                    mode = 1;
                    url = "";
                    while(true)
                        url += r.read();
                }
            }
            catch(Exception e){
                
            }
        }
        this.project = project;
        this.setVisible(true);
        Thread th = new Thread(this);
        th.start();
    }
    
    public void run(){
        File folder = new File("plugins/org/gcreator/compilers/gcpp/Output/");
        folder.mkdir();
        /*BEGIN MAKE MAIN*/
        try{
            System.out.println("Start");
            File __main = new File("plugins/org/gcreator/gcpp/compilers/main.cpp");
            System.out.println("Path: " + __main.getAbsolutePath());
            System.out.println("Main creation checking");
            if(!__main.exists())
                __main.createNewFile();
            System.out.println("Create write buffer");
            FileWriter main = new FileWriter(__main);
            System.out.println("Start writting");
            main.write("#include \"declare.h\"\n");
            main.write("using namespace org::gcreator::Components::Application");
            main.write("using namespace org::gcreator::Components::Sprite");
            main.write("\n");
            main.write("int main(int argc, char** argv){\n");
            main.write("	gameInit(640,480,32,\"tes\");\n");
            main.write("	Game = new Application(argc, argv);");
            main.write("}\n");
            main.write("\n");
            main.close();
            System.out.println("Closed");
            if(mode==0){
                Runtime.getRuntime().exec("make org/gcreator/compilers/gcpp/makefile"); //UNTESTED!!!
            }
            else if(mode==1){
                if(url.replaceAll("\\\"", "!").equals(url))
                    throw new Exception("MinGW path error! Path contains \" sign. For safety reasons, this is not allowed.");
                Runtime.getRuntime().exec("plugins/org/gcreator/compilers/gcpp/mingwmake.bat");
            }
            
        }
        catch(IOException e){
            AddMessage("I/O Error: " + e.getMessage());
        }
        catch(Exception e){
            AddMessage("Error: " + e.getMessage());
        }
    }
    
    private int errors = 0;
    
    public void AddMessage(String message){
        errors++;
        utilities.addStringFormatedMessage(message, "red", false);
        jTextArea1.setText(jTextArea1.getText() + message);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("G-C++ Compiler");

        jLabel1.setText("<html>Welcome to G-C++ compiler. This will compile your game.<br/>Do <b>NOT</b> close the window unless it takes too much time.");

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setForeground(java.awt.Color.red);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Open containing folder");

        jButton2.setText("Run the application");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
    
}
