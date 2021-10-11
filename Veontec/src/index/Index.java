    package index;

import src.Recursos;

public class Index {
    
    public static void main(String[] args) {
        // * Inicializar el programa
        //System.out.println("Inicializando programa...");
        Veontec programa  = new Veontec();
        
        switch (args.length) {
            case 0:
                programa.mtdTagInit();
                break;
            case 1:
                switch( args[0] ){
                    case "--init" : programa.mtdTagInit(); break;
                    case "--pid" : programa.mtdTagPID(); break;
                    case "--test" : programa.mtdTagTest();  break;
                    case "-h" :
                    case "--help" : programa.mtdTagHelp();  break;
                    default: programa.mtdTagHelp(); break;
                }   break;
            default:
                programa.mtdTagHelp();
                break;
        }
        
    }
    
}
