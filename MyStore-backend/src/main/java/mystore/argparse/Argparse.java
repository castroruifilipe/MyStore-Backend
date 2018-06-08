package mystore.argparse;

import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.security.SecureRandom;
import java.util.Random;


/**
 * Classe para fazer parse aos argumentos da linha de comandos
 */
public class Argparse {

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max) {

        Random rand = new SecureRandom();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static Namespace parse(String[] args) {

        ArgumentParser parser = ArgumentParsers.newFor("mystore").build()
                .defaultHelp(true)
                .description("Online store management.");

        parser.addArgument("-createDB", "--createDB", "-db")
                .dest("createDB")
                .action(Arguments.storeTrue())
                .required(false)
                .help("Create the database.");


        parser.addArgument("-nCategories", "--nCategories")
                .dest("nCategories")
                .type(Integer.class)
                .setDefault(20)
                .required(false)
                .help("Number of random categories to create.");

        parser.addArgument("-nClients", "--nClients")
                .dest("nClients")
                .type(Integer.class)
                .setDefault(5)
                .required(false)
                .help("Number of random clients to create.");

        parser.addArgument("-nProducts", "--nProducts")
                .dest("nProducts")
                .type(Integer.class)
                .setDefault(100)
                .required(false)
                .help("Number of random prdducts to create.");


        Namespace ns = null;

        try {
            ns = parser.parseArgs(args);
            return ns;
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
            return null;
        }

    }


}
