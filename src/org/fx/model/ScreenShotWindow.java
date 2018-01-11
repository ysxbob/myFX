package org.fx.model;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShotWindow extends JWindow {
    private int orgx, orgy, endx, endy;
    private Task task;
    private ToolsWindow tools = null;
    private BufferedImage image = null;
    private BufferedImage tempImage = null;
    private BufferedImage saveImage = null;


    public ScreenShotWindow(Task task) throws AWTException {
        this.task = task;
        //获取屏幕尺寸
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, d.width, d.height);

        //截取屏幕
        Robot robot = new Robot();
        image = robot.createScreenCapture(new Rectangle(0, 0, d.width, d.height));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //鼠标松开时记录结束点坐标，并隐藏操作窗口
                orgx = e.getX();
                orgy = e.getY();

                if (tools != null) {
                    tools.setVisible(false);
                }
                System.out.println("mousePressed:"+"orgx:"+orgx+",orgy:"+orgy);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //鼠标松开时，显示操作窗口
                if (tools == null) {
                    tools = new ToolsWindow(ScreenShotWindow.this, e.getX(), e.getY());
                } else {
                    tools.setLocation(e.getX(), e.getY());
                }
                tools.setVisible(true);
                tools.toFront();
                System.out.println("mouseReleased:"+"orgx:"+orgx+",orgy:"+orgy);
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                //鼠标拖动时，记录坐标并重绘窗口
                endx = e.getX();
                endy = e.getY();
                System.out.println("mouseDragged:"+"orgx:"+orgx+",orgy:"+orgy);

                //临时图像，用于缓冲屏幕区域放置屏幕闪烁
                Image tempImage2 = createImage(ScreenShotWindow.this.getWidth(), ScreenShotWindow.this.getHeight());
                Graphics g = tempImage2.getGraphics();
                g.drawImage(tempImage, 0, 0, null);
                int x = Math.min(orgx, endx);
                int y = Math.min(orgy, endy);
                int width = Math.abs(endx - orgx) + 1;
                int height = Math.abs(endy - orgy) + 1;
                // 加上1防止width或height0
                g.setColor(Color.BLUE);
                g.drawRect(x - 1, y - 1, width + 1, height + 1);
                //减1加1都了防止图片矩形框覆盖掉
                saveImage = image.getSubimage(x, y, width, height);
                g.drawImage(saveImage, x, y, null);

                ScreenShotWindow.this.getGraphics().drawImage(tempImage2, 0, 0, ScreenShotWindow.this);
            }
        });
    }
    @Override
    public void paint(Graphics g) {
        RescaleOp ro = new RescaleOp(0.8f, 0, null);
        tempImage = ro.filter(image, null);
        g.drawImage(tempImage, 0, 0, this);
    }

    public void setLocation(){
        task.setX((orgx+endx)/2);
        task.setY((orgy+endy)/2);
        dispose();
    }
    @Override
    public void dispose(){
        super.dispose();
        tools.dispose();
    }

    //保存图像到文件
    public void saveImage() throws IOException {
        JFileChooser jfc=new JFileChooser();
        jfc.setDialogTitle("保存");

        //文件过滤器，用户过滤可选择文件
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG", "jpg");
        jfc.setFileFilter(filter);

        //初始化一个默认文件（此文件会生成到桌面上）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
        String fileName = sdf.format(new Date());
        File filePath = FileSystemView.getFileSystemView().getHomeDirectory();
        File defaultFile = new File(filePath + File.separator + fileName + ".jpg");
        jfc.setSelectedFile(defaultFile);

        int flag = jfc.showSaveDialog(this);
        if(flag==JFileChooser.APPROVE_OPTION){
            File file=jfc.getSelectedFile();
            String path=file.getPath();
            //检查文件后缀，放置用户忘记输入后缀或者输入不正确的后缀
            if(!(path.endsWith(".jpg")||path.endsWith(".JPG"))){
                path+=".jpg";
            }
            //写入文件
            ImageIO.write(saveImage,"jpg",new File(path));
            System.exit(0);
        }
    }
}

/*
* 操作窗口
*/
class ToolsWindow extends JWindow {
    private ScreenShotWindow parent;

    public ToolsWindow(ScreenShotWindow parent, int x, int y) {
        this.parent = parent;
        this.init();
        this.setLocation(x, y);
        this.pack();
        this.setVisible(true);
    }

    private void init() {

        this.setLayout(new BorderLayout());
        JToolBar toolBar = new JToolBar("点击位置");

        //保存按钮
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                    parent.setLocation();
            }
        });
        toolBar.add(saveButton);

        //关闭按钮
        JButton closeButton = new JButton("取消");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.dispose();
            }
        });
        toolBar.add(closeButton);

        this.add(toolBar, BorderLayout.NORTH);
    }

}