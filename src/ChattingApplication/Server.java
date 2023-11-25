package ChattingApplication;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Calendar;
import java.text.*;
import java.net.*;
public class Server implements ActionListener {
    JTextField msg;
    static JPanel p2;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    Server(){

        JPanel p1 = new JPanel();

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/Arrow.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,15,22,22);
        p1.add(back);
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("Icon/VideoCall.png"));
        Image i22 = i11.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i33 = new ImageIcon(i22);
        JLabel video = new JLabel(i33);
        video.setBounds(300,15,25,25);
        p1.add(video);

        ImageIcon c1 = new ImageIcon(ClassLoader.getSystemResource("Icon/call.png"));
        Image c2 = c1.getImage().getScaledInstance(35,35,Image.SCALE_DEFAULT);
        ImageIcon c3 = new ImageIcon(c2);
        JLabel call = new JLabel(c3);
        call.setBounds(355,15,25,25);
        p1.add(call);

        ImageIcon k1 = new ImageIcon(ClassLoader.getSystemResource("Icon/kebab.png"));
        Image k2 = k1.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
        ImageIcon k3 = new ImageIcon(k2);
        JLabel kebab = new JLabel(k3);
        kebab.setBounds(400,15,20,25);
        p1.add(kebab);

        ImageIcon u1 = new ImageIcon(ClassLoader.getSystemResource("Icon/user.png"));
        Image u2 = u1.getImage().getScaledInstance(48,48,Image.SCALE_DEFAULT);
        ImageIcon u3 = new ImageIcon(u2);
        JLabel user = new JLabel(u3);
        user.setBounds(35,5,48,48);
        p1.add(user);

        JLabel name = new JLabel("Nolan");
        name.setBounds(90,8,150,25);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Tahoma",Font.BOLD,16));
        p1.add(name);

        JLabel status = new JLabel("Active now");
        status.setForeground(Color.WHITE);
        status.setBounds(90,25,100,25);
        status.setFont(new Font("Tahoma",Font.BOLD,11));
        p1.add(status);

        p2 = new JPanel();
        p2.setBounds(5,63,440,590);
        f.add(p2);

        msg = new JTextField();
        msg.setBounds(5,660,345,35);
        msg.setFont(new Font("Tahoma",Font.PLAIN,14));
        f.add(msg);

        JButton send = new JButton("Send");
        send.setBounds(355,660,90,35);
        send.setForeground(Color.WHITE);
        send.setBackground(new Color(7,138,94));
        send.addActionListener(this);
        f.add(send);

        p1.setBounds(0,0,450,60);
        p1.setLayout(null);
        p1.setBackground(new Color(7,138,94));
        f.add(p1);

        f.setSize(450,700);
        f.setLayout(null);
        f.getContentPane().setBackground(Color.WHITE);
        f.setLocation(200,100);
        f.setUndecorated(true);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e){
        try{
            String out = msg.getText();
            JLabel output = new JLabel(out);
            JPanel p3  = formatLabel(out);
            //p3.add(output);

            p2.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p3,BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(13));
            p2.add(vertical,BorderLayout.PAGE_START);
            dout.writeUTF(out);
            msg.setText(" ");

            f.repaint();
            f.invalidate();
            f.validate();
        }
        catch(Exception a){
            a.printStackTrace();
        }


    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width : 150px\">"+out+"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,14));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,15));
        panel.add(output);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel() ;
        time.setText(sdf.format(cal.getTime()));
        time.setFont(new Font("Tahoma",Font.PLAIN,11));

        panel.add(time);

        return panel;
    }

    public static void main(String[] args){
        new Server();

        try{
           ServerSocket skt = new ServerSocket(6001);
           while(true){
               Socket s = skt.accept();
               DataInputStream din = new DataInputStream(s.getInputStream());
               dout = new DataOutputStream(s.getOutputStream());
               
               while(true){
                   String msg = din.readUTF();
                   JPanel panel = formatLabel(msg);
                   JPanel left = new JPanel(new BorderLayout());
                   left.add(panel,BorderLayout.LINE_START);
                   vertical.add(left);

                   f.validate();

               }
           }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
