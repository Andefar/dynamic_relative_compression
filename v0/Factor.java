/**
 * Created by andl on 04/02/2016.
 */
abstract class Factor {

}

class TwoFactor extends Factor {
    int p,l;

    TwoFactor(int p, int l) {
        this.p = p;
        this.l = l;
    }

    int getLength() {
        return 2;
    }

    public int getP() {return this.p;}
    public int getL() {return this.l;}
}


class OneFactor extends Factor {
    int c;

    OneFactor(char c) {
        this.c = c;
    }

    public int getP() {return -1;}

    int getLength() {
        return 1;
    }
    public int getChar() {return this.c;}
}

class Test extends Factor {
    int x,y;

    public Test(int a,int b) {
        this.x = a;
        this.y = b;
    }

    public int getX () {return this.x;}
}




