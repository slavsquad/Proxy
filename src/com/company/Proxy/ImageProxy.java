package com.company.Proxy;

import javax.swing.*;
import java.awt.*;
import java.net.*;

public class ImageProxy implements Icon{
    volatile ImageIcon imageIcon;
    final URL imageURL;
    Thread retrievalThread;
    boolean retrieving = false;

    public ImageProxy(URL imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (imageIcon!=null){
            imageIcon.paintIcon(c,g,x,y);
        }else{
            g.drawString("Loading CD cover, please wait...", x+300,y+200);
            if (!retrieving){
                retrieving = true;

                retrievalThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            setImageIcon(new ImageIcon(imageURL,"CD Cover"));
                            c.repaint();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
                retrievalThread.start();
            }
        }
    }

    synchronized void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    @Override
    public int getIconWidth() {
        if(imageIcon!=null){
            return imageIcon.getIconWidth();
        }else{
            return 800;
        }
    }

    @Override
    public int getIconHeight() {
        if(imageIcon!=null){
            return imageIcon.getIconHeight();
        } else {
            return 600;
        }
    }




}
