// core reason: allow generified libraries to compat with non-generified libraries
// remove type information

import java.util.*;

class Frob {}

class Fnorkle {}

class Quark<Q> {}

class Particle<POSITION, MOMENTUM> {}

public class Erasure {
    public static void main(String[] args) {
        List<Frob> lf = new ArrayList<Frob>();
        Map<Frob, Fnorkle> mff = new HashMap<Frob, Fnorkle>();
        Quark<Fnorkle> qf = new Quark<Fnorkle>();
        Particle<Long, Double> pld = new Particle<Long, Double>();

        System.out.println(Arrays.toString(lf.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(mff.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(qf.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(pld.getClass().getTypeParameters()));
    }
}
