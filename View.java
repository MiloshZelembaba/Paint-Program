


import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class View extends JFrame implements Observer {

    private static Model model;
    private Tools toolbar;
    private JPanel canvas;
    private JSlider sizeControll;
    private Thickness thicknessSize;
    private Canvas drawingArea;
    public JSlider scrubber;
    public boolean changingMax = false;
    private JButton play;
    private JButton playBackwards;
    private JButton start;
    private JButton end;
    private JButton colorChooserButton;
    private ColorChooser colorChooser;
    private static JFileChooser fileChooser;


    /**
     * Create a new View.
     */
    public View(Model model) {
        // Set up the window.
        this.setTitle("Deluxe Paint");
        this.setMinimumSize(new Dimension(500, 425));
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        View tmpView = this;
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                promptSave(tmpView);
                System.exit(0);
            }
        });
        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        this.model.addObserver(this);

        BorderLayout borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        setMenuItems();
        setToolbar();
        setScrubber();
        createCanvas();

        setResizeListener();
        setVisible(true);
    }


    public void setResizeListener(){
        /*
            Maintaining the original aspect ratio and notifying listeners of the change
         */
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                int minHeight = 425;
                int minWidth = 500;
                Rectangle b = evt.getComponent().getBounds();
                evt.getComponent().setBounds(b.x, b.y, b.width, b.width*minHeight/minWidth);
                toolbar.onResize(getWidth(), getHeight());
                drawingArea.onResize();
            }
        });
    }

    public void notifyColorChange(Color color){
        model.color = color;
        thicknessSize.color = color;
        thicknessSize.repaint();
    }

    private void setScrubber(){
        Box bottom = new Box(BoxLayout.X_AXIS);
        bottom.setBorder(new LineBorder(Color.BLACK));
        play = new JButton("Play");
        playBackwards = new JButton("Play Backwards");
        bottom.add(play);
        bottom.add(playBackwards);
        scrubber = new JSlider();
        scrubber.setMinimum(0);
        scrubber.setMaximum(0);
        scrubber.setPaintTicks(true);
        scrubber.setMajorTickSpacing(10);

        scrubber.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = scrubber.getValue();
                if (value == 0){
                    play.setEnabled(true);
                    playBackwards.setEnabled(false);
                } else if (value == scrubber.getMaximum()){
                    play.setEnabled(false);
                    playBackwards.setEnabled(true);
                } else {
                    play.setEnabled(true);
                    playBackwards.setEnabled(true);
                }
                if (!changingMax) {
                    model.redrawPortion(value);
                }
            }
        });
        bottom.add(scrubber);
        start = new JButton("Start");
        bottom.add(start);
        end = new JButton("End");
        bottom.add(end);
        setButtonListeners();
        this.add(bottom, BorderLayout.SOUTH);
    }

    private void setButtonListeners(){
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scrubber.setValue(0);
            }
        });
        end.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scrubber.setValue(scrubber.getMaximum());
            }
        });
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playScrubber(1);
            }
        });
        playBackwards.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playScrubber(-1);
            }
        });
    }

    public void playScrubber(int direction){
        int startingPoint = scrubber.getValue();

        for (int i = startingPoint; i >= 0 && i<=scrubber.getMaximum(); i+=direction){
            scrubber.setValue(i);
            model.redrawPortion(i);
//            for (int j=0; j<30000; ++j){
//                System.out.print("sorry");
//            }
            try {
                Thread.sleep(100);
            } catch (Exception e){
                // do nothing?
            }
        }
    }

    public void draw(Image tmp){
        drawingArea.draw(tmp);
        drawingArea.paint(drawingArea.getGraphics());
    }

    public void clear(){
        drawingArea.clear();
    }

    private void createCanvas(){
        canvas = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        canvas.setLayout(borderLayout);
        canvas.add(Box.createVerticalStrut(1), BorderLayout.SOUTH);
        canvas.add(Box.createHorizontalStrut(1), BorderLayout.WEST);
        drawingArea = new Canvas(model,this);
        drawingArea.addMouseListener(drawingArea);
        drawingArea.addMouseMotionListener(drawingArea);
        drawingArea.setBackground(Color.WHITE);
        canvas.add(drawingArea);
        canvas.setBackground(Color.BLACK);
        this.add(canvas);
    }

    private void setToolbar(){
        toolbar = new Tools(this);
        toolbar.setBackground(Color.GRAY);
        Box tmp = new Box(BoxLayout.Y_AXIS);
        tmp.setMaximumSize(new Dimension(200, 250));
        tmp.setMinimumSize(new Dimension(200, 250));
        tmp.add(toolbar);
        colorChooserButton = new JButton("Color Chooser");
        View tmpView = this;
        colorChooserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                colorChooser = new ColorChooser(tmpView);

            }
        });
        colorChooserButton.setAlignmentX(0.5f);
        tmp.add(colorChooserButton);
        Label label = new Label("Thickness", Label.CENTER);
        label.setMaximumSize(new Dimension(150,20));
        tmp.add(label);
        tmp.add(Box.createVerticalStrut(10));
        thicknessSize = new Thickness();
        thicknessSize.setMaximumSize(new Dimension(50,50));
        thicknessSize.setBackground(Color.DARK_GRAY);
        tmp.add(thicknessSize);
        sizeControll = new JSlider();
        setupListenerForSlider();
        sizeControll.setMaximumSize(new Dimension(110,50));
        sizeControll.setSize(new Dimension(110,10));
        tmp.add(sizeControll);
        tmp.setBorder(new LineBorder(Color.BLACK));  // tmp
        this.add(tmp, BorderLayout.WEST);
    }

    private void setupListenerForSlider(){
        sizeControll.setMinimum(3);
        sizeControll.setMaximum(50);
        sizeControll.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                thicknessSize.size = sizeControll.getValue();
                thicknessSize.repaint();
                model.size = sizeControll.getValue();
            }
        });
    }

    private void setMenuItems(){
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem newCanvas = new JMenuItem("New");
        JMenuItem close = new JMenuItem("Exit");
        View tmpView = this;
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                model.saveChanges(tmpView, fileChooser);
            }
        });
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("milosh file","milosh"));
                File workingDirectory = new File(System.getProperty("user.dir"));
                fileChooser.setCurrentDirectory(workingDirectory);
                promptSave(tmpView);
                int retrival = fileChooser.showOpenDialog(tmpView);
                if (retrival == JFileChooser.APPROVE_OPTION) {
                    try {
                        Image img = FileOpener.openFile(fileChooser.getSelectedFile().getAbsolutePath());
                        model.reset();
                        drawingArea.clear();
                        scrubber.setMaximum(0);
                        scrubber.setValue(0);
                        drawingArea.setImage(img);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        JFrame tmp = this;
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispatchEvent(new WindowEvent(tmp, WindowEvent.WINDOW_CLOSING));
            }
        });
        newCanvas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promptSave(tmpView);
                model.reset();
                drawingArea.clear();
                scrubber.setMaximum(0);
                scrubber.setValue(0);
            }
        });
        file.add(newCanvas);
        file.add(open);
        file.add(save);
        file.add(close);

        menuBar.add(file);
        f.setJMenuBar(menuBar);
    }

    private static void promptSave(View tmpView){
        while (model.hasUnsavedChanges()){
            int result = JOptionPane.showConfirmDialog((Component) null, "You have unsaved changes",
                    "Unsaved Changes", JOptionPane.OK_CANCEL_OPTION);
            if (result == 0){
                model.saveChanges(tmpView,fileChooser);
            } else {
                break;
            }
        }
    }


    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        System.out.println("Model changed!");
    }

    public void lineAddition(int numLines){
        changingMax = true;
//        System.out.println("line addition hook: " + numLines);
        scrubber.setMaximum(numLines*10);
        scrubber.setValue(numLines*10);
        changingMax = false;

    }
}
