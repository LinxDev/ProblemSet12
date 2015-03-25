
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class implements the visual and interactive component of Conway's
 * Game of Life.  This class is both a JComponent as well as a mouse event handler
 * (listener).
 */
public class LifeViewer extends JComponent implements MouseListener
{
    private static final int ROWS = 80;
    private static final int COLS = 100;

    private int fGeneration;
    private boolean fPopulationEditorActive;

    private final int X_GRID_OFFSET = 25; // 10 pixels from left
    private final int Y_GRID_OFFSET = 40; // 10 pixels from top
    private final int CELL_WIDTH = 5;
    private final int CELL_HEIGHT = 5;

    // Note that a final field can be initialized in constructor
    private final int fDisplayWidth;
    private final int fDisplayHeight;

    // Components for the population editor
    private SettingsButton fSettingsButton;
    private DoneButton fSettingsDoneButton;

    // Buttons to control simulation
    private StartButton fStartButton;
    private StepButton fStepButton;
    private ClearButton fClearButton;
    private ResetButton fResetButton;

    // Optional component that let's you control the speed of the simulation
    private SpeedSlider fSpeedSlider;

    // Variable that tracks whether simulation is actually running or not
    private boolean fIsGameRunning;

    // Variable that tracks the current speed between generations.  Higher
    // numbers are slower speeds.  This is actually the number of milliseconds
    // to wait between generations.
    private int fGenerationSpeed;

    public LifeViewer( int width, int height )
    {
        fDisplayWidth = width;
        fDisplayHeight = height;

        fGeneration = 0;

        fPopulationEditorActive = false;

        fIsGameRunning = false;

        fGenerationSpeed = 100;

        init();
    }


    public void init()
    {
        setSize( fDisplayWidth, fDisplayHeight );

        addMouseListener( this );

        //sets locations and initial settings of buttons
        fStartButton = new StartButton();
        fStartButton.setBounds( 25, 550, 100, 36 );
        add( fStartButton );
        fStartButton.setVisible( true );

        fSettingsButton = new SettingsButton();
        fSettingsButton.setBounds( 525, 550, 100, 36 );
        add( fSettingsButton );
        fSettingsButton.setVisible( true );

        fSettingsDoneButton = new DoneButton();
        fSettingsDoneButton.setBounds( 225, 550, 100, 36 );
        add( fSettingsDoneButton );
        fSettingsDoneButton.setVisible( false );
        fSettingsDoneButton.setEnabled( false );

        fStepButton = new StepButton();
        fStepButton.setBounds( 125, 550, 100, 36 );
        add( fStepButton );
        fStepButton.setVisible( true );

        fSpeedSlider = new SpeedSlider();
        fSpeedSlider.setBounds( 325, 550, 100, 36 );
        add( fSpeedSlider );
        fSpeedSlider.setVisible( true );

        fClearButton = new ClearButton();
        fClearButton.setBounds( 325, 550, 100, 36 );
        add( fClearButton );
        fClearButton.setVisible( false );
        fClearButton.setEnabled( false );

        fResetButton = new ResetButton();
        fResetButton.setBounds( 425, 550, 100, 36 );
        add( fResetButton );
        fResetButton.setVisible( true );
        fResetButton.setEnabled( true );

        repaint();
    }


    public void paintComponent( Graphics g )
    {
        g.setColor( Color.BLACK );

        drawGrid( g );

        if ( fIsGameRunning )
        {
            // The following code causes the system to wait between generations, effectively
            // slowing down the simulator.
            try
            {
                Thread.sleep( fGenerationSpeed );
            }
            catch ( InterruptedException e )
            {
                e.printStackTrace();
            }

            // disable certain buttons when the simulation is running
            fSettingsButton.setEnabled( false );
            fStepButton.setEnabled( false );
            fResetButton.setEnabled( false );

            repaint();
        }
        else
        {
            // make sure these buttons are enabled when simulation is not running

            fSettingsButton.setEnabled( true );
            fStepButton.setEnabled( true );
            fResetButton.setEnabled( true );
        }

        if ( fPopulationEditorActive )
        {
            // If the editor is active, then the game cannot also be running
            fIsGameRunning = false;

            // Enable and disable the appropriate buttons

            // TODO: This need to be fixed so that the buttons show and don't show based
            // on the current state of the editor
            fStartButton.setEnabled( false );

            fSpeedSlider.setEnabled( false );
            fStepButton.setEnabled( false );
            fSettingsButton.setEnabled( false );
            fSettingsButton.setVisible( false );
            fStartButton.setVisible( false );

            fSpeedSlider.setVisible( false );
            fStepButton.setVisible( false );

            fSettingsDoneButton.setEnabled( true );
            fSettingsDoneButton.setVisible( true );
            fClearButton.setEnabled( true );
            fClearButton.setVisible( true );

            repaint();
        }

        if ( fStartButton.isSelected() )
        {
            // start the simulation if the Start button is clicked
            fIsGameRunning = true;
        }

        String turn1 = Integer.toString( fGeneration );
        g.drawString( "Generation: " + turn1, 500, 600 );
        g.drawString( "*Reset sets all shapes to their original form regardless of turn implemented", 25, 650 );

        repaint();
    }


    /**
     * Toggles between the simulation running and not running.  Called when the Start
     * button is pressed.
     */
    public void toggleSimulationRunState()
    {
        fIsGameRunning = !fIsGameRunning;
    }


    /**
     * Draws the basic grid onto the component.
     *
     * @param g the graphics component
     */
    private void drawGrid( Graphics g )
    {
        for ( int row = 0; row <= ROWS; row++ )
        {
            g.drawLine( X_GRID_OFFSET,
                        Y_GRID_OFFSET + ( row * ( CELL_HEIGHT + 1 ) ), X_GRID_OFFSET
                            + COLS * ( CELL_WIDTH + 1 ), Y_GRID_OFFSET
                                + ( row * ( CELL_HEIGHT + 1 ) ) );
        }

        for ( int col = 0; col <= COLS; col++ )
        {
            g.drawLine( X_GRID_OFFSET + ( col * ( CELL_WIDTH + 1 ) ), Y_GRID_OFFSET,
                        X_GRID_OFFSET + ( col * ( CELL_WIDTH + 1 ) ), Y_GRID_OFFSET
                            + ROWS * ( CELL_HEIGHT + 1 ) );
        }
    }

    /**
     * Called when then mouse is clicked on this component.
     *
     * @param e the mouse event to process
     */
    public void mouseClicked( MouseEvent e )
    {

    }

    /**
     * Called when the mouse enters this component.
     *
     * @param e the mouse event
     */
    public void mouseEntered( MouseEvent e )
    {
        // not used
    }


    /**
     * Called when the mouse exits this component.
     *
     * @param e the mouse event
     */
    public void mouseExited( MouseEvent e )
    {
        // not used
    }


    /**
     * Called when a mouse button has been pressed on this component.  This
     * is useful in the population editor so that you can press once and then
     * drag over a number of cells to toggle them.
     *
     * @param e the mouse event
     */
    public void mousePressed( MouseEvent e )
    {
        // TODO: provide implementation
        
    }

    /**
     * Called when the mouse button has been released.
     *
     * @param e the mouse event
     */
    public void mouseReleased( MouseEvent e )
    {
        // not used
    }


    /**
     * This class implements the start button.  We do this by toggling the name
     * of the button so that it switches from Start to Stop and then back to Start
     * each time it is pressed.
     *
     * This class implements ActionListener
     */
    private class StartButton extends JButton implements ActionListener
    {
        public StartButton()
        {
            super( "Start" );
            addActionListener( this );
        }

        /**
         * React to the button press.
         *
         * @param e the action
         */
        public void actionPerformed( ActionEvent e )
        {
            // first swap the button label
            if ( getText().equals( "Start" ) )
            {
                setText( "Stop" );
            }
            else
            {
                setText( "Start" );
            }

            // now toggle the state of the simulation
            toggleSimulationRunState();
            repaint();
        }
    }

    /**
     * This class implements the generation step button.  Clicking this button causes
     * the simulation to advance one step at a time.
     */
    private class StepButton extends JButton implements ActionListener
    {
        public StepButton()
        {
            super( "Step " );
            addActionListener( this );
        }

        public void actionPerformed( ActionEvent e )
        {
            // TODO: To be implemented by you


            repaint();
        }
    }

    /**
     * This class implements the population editor button.  Pressing this button
     * puts the user into population editor mode.
     */
    private class SettingsButton extends JButton implements ActionListener
    {
        public SettingsButton()
        {
            super( "Editor" );
            addActionListener( this );
        }

        public void actionPerformed( ActionEvent e )
        {
            // set the population editor active flag
            fPopulationEditorActive = true;

            repaint();
        }
    }

    /**
     * This class implements the population editor finished (done) button.  Pressing
     * this button exits the user from the population editor.
     */
    private class DoneButton extends JButton implements ActionListener
    {
        public DoneButton()
        {
            super( "Done" );
            addActionListener( this );
        }

        public void actionPerformed( ActionEvent e )
        {
            // set the population editor active flag to false
            fPopulationEditorActive = false;

            repaint();
        }
    }

    /**
     * This class implements a slider widget that will allow the user to select
     * a simulation speed.
     */
    private class SpeedSlider extends JSlider implements ChangeListener
    {
        public SpeedSlider()
        {
            super( 0, 100 );
            addChangeListener( this );
        }

        public void stateChanged( ChangeEvent e )
        {
            // the low end of the slider means slower speed
            fGenerationSpeed = 100 - getValue();

            repaint();
        }
    }

    /**
     * The clear button is used to reset the all the cells on the board back
     * to the empty state.
     */
    private class ClearButton extends JButton implements ActionListener
    {
        public ClearButton()
        {
            super( "Clear" );
            addActionListener( this );
        }

        public void actionPerformed( ActionEvent e )
        {
            // TODO: To be implemented by you

            repaint();
        }
    }

    /**
     * The reset button is used to reset the simulation back to the state after the
     * last time the user exited the population editor.
     */
    private class ResetButton extends JButton implements ActionListener
    {
        public ResetButton()
        {
            super( "*Reset" );
            addActionListener( this );
        }

        public void actionPerformed( ActionEvent e )
        {
            // TODO: To be implemented by you

            repaint();
        }
    }
}


