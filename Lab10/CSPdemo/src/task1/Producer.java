package task1;
import org.jcsp.lang.*;

/** Producer class: produces one random integer and sends on
  * output channel, then terminates.
  */
public class Producer implements CSProcess
  { private One2OneChannelInt[] out;

    public Producer (One2OneChannelInt[] out)
      { this.out = out;
      } // constructor

    public void run ()
      {
//        Guard[] guards = new Guard[out.length];
//        System.arraycopy(out, 0, guards, 0, out.length);
//        Guard[] guards = { out[0], out[1] };
//        final Alternative alt = new Alternative(guards);
        for(int i=0; i<20; i++) {
//          int index = alt.fairSelect();
          int item = (int) (Math.random() * 100) + 1;
          System.out.println("I am sending " + item);
          out[i % 10].out().write(item);
        }

      } // run

  } // class Producer
