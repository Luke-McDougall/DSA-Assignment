public class RBTest
{
    public static void main(String[] args)
    {
        RedBlackTree<Integer> t = new RedBlackTree<>();
        char c = 'a';
        for(int i = 0; i < 12; i++)
        {
            String entry = Character.toString(c + i);
            t.insert(entry, i);
        } 
        t.print();
        System.out.printf("Height = %d, size = %d\n", t.height(), t.getSize());
        t.redBlackCorrect();
    }
}
