/*
 * INF4230 - Intelligence artificielle
 * UQAM - Département d'informatique
 */

package planeteH_2;

import javax.swing.*;

/**
 *
 */
public class FenetrePlaneteH_2 extends javax.swing.JFrame
 implements GrilleDisplayListener, JeuObserver {
    
    protected GrilleDisplay display;
    
    protected Grille grille = new Grille(12, 17);
    protected GrilleVerificateur verificateur = new GrilleVerificateur();
    
    protected JoueurGUI joueurGUI = null;
    public    JeuPlaneteH_2 gameplay = null;
    
    protected BoiteConfiguration config;
    
    /** Creates new form Client5Frame */
    public FenetrePlaneteH_2() {
        initComponents();
        display = new GrilleDisplay();
        display.setGrille(grille);
        display.setCaseListener(this);
        grilleScrollpane.setViewportView(display);
        pack();
        
        joueurGUI = new JoueurGUI();
        gameplay = new JeuPlaneteH_2(joueurGUI, joueurGUI, 12, 17);
        gameplay.setGrilleObserver(this);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    demarrerButton = new javax.swing.JButton();
    stopButton = new javax.swing.JButton();
    configButton = new javax.swing.JButton();
    grilleScrollpane = new javax.swing.JScrollPane();
    jScrollPane1 = new javax.swing.JScrollPane();
    messageTextArea = new javax.swing.JTextArea();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("INF4230 - Planete H: La bataille");

    demarrerButton.setText("Démarrer");
    demarrerButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        demarrerButtonActionPerformed(evt);
      }
    });
    jPanel1.add(demarrerButton);

    stopButton.setText("Arrêter");
    stopButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        stopButtonActionPerformed(evt);
      }
    });
    jPanel1.add(stopButton);

    configButton.setText("Configuration");
    configButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        configButtonActionPerformed(evt);
      }
    });
    jPanel1.add(configButton);

    getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);
    getContentPane().add(grilleScrollpane, java.awt.BorderLayout.CENTER);

    messageTextArea.setColumns(20);
    messageTextArea.setEditable(false);
    messageTextArea.setRows(5);
    jScrollPane1.setViewportView(messageTextArea);

    getContentPane().add(jScrollPane1, java.awt.BorderLayout.SOUTH);
  }// </editor-fold>//GEN-END:initComponents

    private void demarrerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_demarrerButtonActionPerformed

        //TODO remove below decomment below
        /*Joueur[] joueurs = new Joueur[]{new JoueurArtificiel(),new JoueurArtificiel()};
        for(int i=0;i<joueurs.length;i++)
            if(joueurs[i]==null) joueurs[i] = joueurGUI;

        gameplay.setJoueurs(joueurs);*/
        //TODO remove above
        messageTextArea.setText("");
        demarrerButton.setEnabled(false);
        configButton.setEnabled(false);
        Runnable task = new Runnable(){
            @Override
            public void run(){
                try{
                    joueurGUI.clear();
                    System.gc();
                    gameplay.run();
                    System.gc();
                }catch(Exception e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(FenetrePlaneteH_2.this, e);
                }
                demarrerButton.setEnabled(true);
                configButton.setEnabled(true);
            }
        };
        Thread t = new Thread(task);
        t.start();
        
}//GEN-LAST:event_demarrerButtonActionPerformed

    private void configButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configButtonActionPerformed
        if(config==null) config = new BoiteConfiguration(this, true);
        config.setVisible(true);
        gameplay.setSize(Integer.parseInt(config.nbLignesTF.getText()),
                         Integer.parseInt(config.nbLignesTF.getText())+5);


        Joueur[] joueurs = config.getJoueurs();
        for(int i=0;i<joueurs.length;i++)
            if(joueurs[i]==null) joueurs[i] = joueurGUI;
        
        gameplay.setJoueurs(joueurs);
        gameplay.setTempsAlloue(Integer.parseInt(config.delaisReflexion.getText()));

        gameplay.setTolerer50pDepassement(config.tolerate50pCB.isSelected());
        gameplay.setIgnorerRetard(config.ignorerRetardCB.isSelected());

        display.setGrille(gameplay.getGrille());
        message("Configuration terminée.");
        System.gc();
    }//GEN-LAST:event_configButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        gameplay.forceStop();
        joueurGUI.setProchainCoup(0, 0);
    }//GEN-LAST:event_stopButtonActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FenetrePlaneteH_2().setVisible(true);
            }
        });
    }
    
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton configButton;
  private javax.swing.JButton demarrerButton;
  private javax.swing.JScrollPane grilleScrollpane;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTextArea messageTextArea;
  private javax.swing.JButton stopButton;
  // End of variables declaration//GEN-END:variables

    @Override
    public void caseClicked(int ligne, int colonne) {
        joueurGUI.setProchainCoup(ligne, colonne);
    }
    
    public class JoueurGUI implements Joueur {

        public synchronized void setProchainCoup(int l, int c){
            prochainCoup = new Position(l, c);
            notifyAll();
        }
        
        public Position getProchainCoup(Grille g, int delais) {
            prochainCoup = null;
            while(true){
                if(prochainCoup==null){
                    synchronized(this){
                        try{
                            wait();
                        }catch(InterruptedException ie) {ie.printStackTrace();}
                    }
                    continue;
                }
                if(g.get(prochainCoup)!=0){
                    prochainCoup = null;
                    continue;
                }
                return prochainCoup;
            }
        }
        
        private Position prochainCoup = null;

        private void clear() {
            prochainCoup = null;
        }

        @Override
        public String getAuteurs() {
            return "Interface manuelle";
        }
    }

    @Override
    public void grilleChanged(Grille g) {
        display.setGrille(g);
    }

    @Override
    public void message(String msg) {
        messageTextArea.append(msg + "\n");
        messageTextArea.setCaretPosition(messageTextArea.getText().length());
    }

    public void clearMessage(){
        messageTextArea.setText("");
    }
    
}
