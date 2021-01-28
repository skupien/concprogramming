package task1;
import org.jcsp.lang.*;

public class BufferReader implements CSProcess {

    private ChannelInputInt in;
    //    private AltingChannelInputInt[] in;
    private
    private ChannelOutputInt out;

    public Buffer(AltingChannelInputInt[] in, ChannelOutputInt out) {
        this.in = in;
        this.out = out;
    }

    public void run() {
        Guard[] guards = {., }
        while(true) {
            final Alternative alt = new Alternative(in);
            int index = alt.select();
            out.write(in[index].read());
        }
    }
}
