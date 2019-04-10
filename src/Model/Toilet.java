package Model;

import javax.swing.*;

public class Toilet extends Block implements Activable {
    private static JLabel lblClock = new JLabel("");
    private String time;
    public Toilet(int x, int y, int color) {
        super(x, y, color);
    }

    @Override
    public Player activate(Player mainChar) throws InterruptedException {
        JOptionPane jop = new JOptionPane();
        int option = jop.showConfirmDialog(null, "Voulez-vous aller aux toilettes ?", "Confirmez", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            if (mainChar.energy < 100) {

                System.out.println("fini1");
                final JOptionPane optionPane = new JOptionPane(lblClock, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                System.out.println("fini2");
                // Thread t = new SampleThread(lblClock, optionPane);
                //  t.start();

                final JDialog dialog = new JDialog();
                dialog.setTitle("Message");
                dialog.setModal(true);
                dialog.setLocationRelativeTo(null);
                dialog.setContentPane(optionPane);
                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                dialog.pack();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=3;i>0;i--){

                            try {
                                if(i==0){

                                }
                                time = (Integer.toString(i));
                                System.out.println("te");
                                lblClock.setText(time);
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        mainChar.bladder -= 10;
                        if(mainChar.bladder < 0) {
                            mainChar.bladder = 0;
                        }
                        dialog.dispose();
                    }

                }).start();

                dialog.setVisible(true);

                //  t.join();
                System.out.println("afterclock");
                dialog.dispose();
                //  jop.showMessageDialog(null, lblClock, "Attention !", JOptionPane.DEFAULT_OPTION);

            } else {
                jop.showMessageDialog(null, "Vous ne devez pas aller aux toilettes", "Attention !", JOptionPane.INFORMATION_MESSAGE);
            }


        }
        return mainChar;
    }

    @Override
    public boolean isObstacle() {
        return true;
    }
}
