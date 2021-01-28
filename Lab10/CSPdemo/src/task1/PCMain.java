package task1;
import org.jcsp.lang.*;

/** Main program class for Producer/Consumer example.
  * Sets up channel, creates one of each process then
  * executes them in parallel, using JCSP.
  */
public final class PCMain
  {
    public static void main (String[] args)
      { new PCMain();
      } // main

    public PCMain ()
      { // Create channel object
        final One2OneChannelInt[] a = Channel.one2oneIntArray(10);
        final One2OneChannelInt b = Channel.one2oneInt();
        // Create and run parallel construct with a list of processes
        CSProcess[] procList = {
                new Producer(a),
                new Buffer(Channel.getInputArray(a), b.out()),
                new Consumer(b)
        }; // Processes
        

        Parallel par = new Parallel(procList); // PAR construct
        par.run(); // Execute processes in parallel
      } // PCMain constructor

  } // class PCMain
