
import javax.swing.*;

/**
 * This class is the main program for Conway's Game of Life.
 * You should not need to modify anything in this class.
 */
public class GameOfLife
{
    static final int DISPLAY_WIDTH  = 665;

    static final int DISPLAY_HEIGHT = 700;


    public static void main( String[] args )
    {
        JFrame f = new JFrame();

        f.setSize( DISPLAY_WIDTH, DISPLAY_HEIGHT );

        LifeViewer display = new LifeViewer( DISPLAY_WIDTH, DISPLAY_HEIGHT );

        f.setLayout( null );

        f.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );

        f.setTitle( "Game of Life" );

        f.add( display );

        f.setVisible( true );
    }

}
