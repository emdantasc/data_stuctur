import java.util.LinkedList;
import java.util.Iterator;

// A classe Mergesort a ser completada
class Mergesort {

    static void split(LinkedList<Integer> l, LinkedList<Integer> l1, LinkedList<Integer> l2) {
        Iterator<Integer> l_it = l.iterator();

        int counter = 0, size = l.size();

        if (l.isEmpty()) {
            return;
        } else {
            while (l_it.hasNext()) {
                if (counter < (size / 2 + size % 2)) {
                    l1.add(l_it.next());
                    counter++;
                } else {
                    l2.add(l_it.next());
                }
            }
            return;
        }

    }

    static LinkedList<Integer> merge(LinkedList<Integer> l1, LinkedList<Integer> l2) {
        LinkedList<Integer> l = new LinkedList<Integer>();

        if (l1.isEmpty() && l2.isEmpty()) {
            return l;
        } else if (!l1.isEmpty() && l2.isEmpty()) {
            return l1;
        } else if (l1.isEmpty() && !l2.isEmpty()) {
            return l2;
        } else {
            Iterator<Integer> l1_it = l1.iterator();
            Iterator<Integer> l2_it = l2.iterator();

            Integer l1_value = l1_it.next(), l2_value = l2_it.next();

            boolean l1_hasvalue = true;
            boolean l2_hasvalue = true;

            if (!l1_it.hasNext() && !l2_it.hasNext() && l1_hasvalue && l2_hasvalue) {
                if (l1_value < l2_value) {
                    l.add(l1_value);
                    l.add(l2_value);
                    l1_hasvalue = false;
                    l2_hasvalue = false;
                } else {
                    l.add(l2_value);
                    l.add(l1_value);
                    l2_hasvalue = false;
                    l1_hasvalue = false;
                }
            }
            while (l1_it.hasNext() || l2_it.hasNext()) {
                if (!l1_hasvalue && l1_it.hasNext()) {
                    l1_value = l1_it.next();
                    l1_hasvalue = true;
                }
                if (!l2_hasvalue && l2_it.hasNext()) {
                    l2_value = l2_it.next();
                    l2_hasvalue = true;
                }

                if ((l1_value <= l2_value) && l1_hasvalue) {
                    l.add(l1_value);
                    l1_hasvalue = false;
                } 
                else if (!(l1_value <= l2_value) && l2_hasvalue){
                    l.add(l2_value);
                    l2_hasvalue = false;
                }

                if(!l1_it.hasNext() && l2_hasvalue){
                    l.add(l2_value);
                    l2_hasvalue=false;
                }
                else if(!l2_it.hasNext() && l1_hasvalue){
                    l.add(l1_value);
                    l1_hasvalue=false;
                }
            }
            return l;
        }
    }

    static LinkedList<Integer> mergesort(LinkedList<Integer> l) {
        if(l.size()<=1){return l;}
        else{
            LinkedList<Integer> l1 = new LinkedList<Integer>();
            LinkedList<Integer> l2 = new LinkedList<Integer>();

            split(l, l1, l2);

            l=merge(mergesort(l1), mergesort(l2));
            
            return l;
        }

    }
}

// A classe Ex1 � fornecida fournie, para testar o c�digo de Mergesort
class Ex1 {

    static boolean is_sorted(LinkedList<Integer> l) {
        int v = Integer.MIN_VALUE;
        for (int x : l) {
            if (!(v <= x))
                return false;
            v = x;
        }
        return true;
    }

    static final int M = 10; // os elementos est�o entre 0..M-1

    static int[] occurrences(LinkedList<Integer> l) {
        int[] occ = new int[M];
        for (int x : l)
            occ[x]++;
        return occ;
    }

    static boolean is_permut(LinkedList<Integer> l1, LinkedList<Integer> l2) {
        int[] occ1 = occurrences(l1);
        int[] occ2 = occurrences(l2);
        for (int i = 0; i < M; i++)
            if (occ1[i] != occ2[i])
                return false;
        return true;
    }

    static void test(LinkedList<Integer> l) {
        System.out.println("           l = " + l);
        int[] old_occ = occurrences(l);
        LinkedList<Integer> sl = Mergesort.mergesort(l);
        int[] new_occ = occurrences(l);
        for (int i = 0; i < M; i++)
            if (old_occ[i] != new_occ[i]) {
                System.out.println("ERRO : mergesort modificou seu parametro");
                System.exit(1);
            }
        System.out.println("mergesort(l) = " + sl);
        if (!is_sorted(sl)) {
            System.out.println("ERRO: o resultado nao esta ordenado");
            System.exit(1);
        }
        if (!is_permut(l, sl)) {
            System.out.println("ERRO : os elementos diferem");
            System.exit(1);
        }
    }

    static LinkedList<Integer> random_list(int len) {
        LinkedList<Integer> l = new LinkedList<Integer>();
        for (int i = 0; i < len; i++)
            l.add((int) (M * Math.random()));
        return l;
    }

    public static void main(String[] args) {
        System.out.println("teste de split");
        for (int len = 0; len < 10; len++) {
            LinkedList<Integer> l = random_list(len);
            System.out.println("         l = " + l);
            int occ[] = occurrences(l);
            LinkedList<Integer> l1 = new LinkedList<Integer>(), l2 = new LinkedList<Integer>();
            Mergesort.split(l, l1, l2);
            int[] new_occ = occurrences(l);
            for (int i = 0; i < M; i++)
                if (occ[i] != new_occ[i]) {
                    System.out.println("ERRO : split modificou seu parametro (l = " + l + ")");
                    System.exit(1);
                }
            System.out.println("  split(l) = " + l1 + " / " + l2);
            int occ0[] = occurrences(l1);
            int occ1[] = occurrences(l2);
            for (int i = 0; i < M; i++)
                if (occ0[i] + occ1[i] != occ[i]) {
                    System.out.println("ERRO : os elementos diferem");
                    System.exit(1);
                }
        }
        System.out.println("teste de merge");
        for (int len = 0; len < 5; len++) {
            LinkedList<Integer> l1 = new LinkedList<Integer>(), l2 = new LinkedList<Integer>();
            for (int i = 0; i < len; i++) {
                l1.add(2 * i);
                l2.add(2 * i + 1);
            }
            System.out.println("            l1 = " + l1);
            System.out.println("            l2 = " + l2);
            int occ1[] = occurrences(l1);
            int occ2[] = occurrences(l2);
            LinkedList<Integer> l = Mergesort.merge(l1, l2);
            System.out.println("  merge(l1,l2) = " + l);
            if (!is_sorted(l)) {
                System.out.println("ERRO : o resultado nao esta ordenado");
                System.exit(1);
            }
            int occ[] = occurrences(l);
            for (int i = 0; i < M; i++)
                if (occ1[i] + occ2[i] != occ[i]) {
                    System.out.println("ERRO : os elementos diferem");
                    System.exit(1);
                }
        }
        System.out.println("teste de mergesort");
        for (int len = 0; len < 10; len++)
            for (int j = 0; j <= len; j++)
                test(random_list(len));
        System.out.println("SUCESSO");
    }

}
