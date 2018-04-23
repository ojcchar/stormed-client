package ch.usi.inf.reveal.parsing.stormed.service;

import org.junit.Test;

public class StormedClientTest2 {

    @Test
    public void parseText() {

        final String codeToParse =
                "wrong result in eigen decomposition. \n" +
                        "Some results computed by EigenDecompositionImpl are wrong. The following case computed by " +
                        "Fortran " +
                        "Lapack fails with version 2.0\n" +
                        "{code}\n" +
                        "    public void testMathpbx02() {\n" +
                        "\n" +
                        "        double[] mainTridiagonal = {\n" +
                        "        \t  7484.860960227216, 18405.28129035345, 13855.225609560746,\n" +
                        "        \t 10016.708722343366, 559.8117399576674, 6750.190788301587, \n" +
                        "        \t    71.21428769782159\n" +
                        "        };\n" +
                        "        double[] secondaryTridiagonal = {\n" +
                        "        \t -4175.088570476366,1975.7955858241994,5193.178422374075, \n" +
                        "        \t  1995.286659169179,75.34535882933804,-234.0808002076056\n" +
                        "        };\n" +
                        "\n" +
                        "        // the reference values have been computed using routine DSTEMR\n" +
                        "        // from the fortran library LAPACK version 3.2.1\n" +
                        "        double[] refEigenValues = {\n" +
                        "        \t\t20654.744890306974412,16828.208208485466457,\n" +
                        "        \t\t6893.155912634994820,6757.083016675340332,\n" +
                        "        \t\t5887.799885688558788,64.309089923240379,\n" +
                        "        \t\t57.992628792736340\n" +
                        "        };\n" +
                        "        RealVector[] refEigenVectors = {\n" +
                        "        \t\tnew ArrayRealVector(new double[] {-0.270356342026904, 0.852811091326997, " +
                        "0.399639490702077, 0.198794657813990, 0.019739323307666, 0.000106983022327, " +
                        "-0.000001216636321}),\n" +
                        "        \t\tnew ArrayRealVector(new double[] {0.179995273578326,-0.402807848153042," +
                        "0.701870993525734,0.555058211014888,0.068079148898236,0.000509139115227,-0.000007112235617})" +
                        ",\n" +
                        "        \t\tnew ArrayRealVector(new double[] {-0.399582721284727,-0.056629954519333," +
                        "-0.514406488522827,0.711168164518580,0.225548081276367,0.125943999652923," +
                        "-0.004321507456014}),\n" +
                        "        \t\tnew ArrayRealVector(new double[] {0.058515721572821,0.010200130057739," +
                        "0.063516274916536,-0.090696087449378,-0.017148420432597,0.991318870265707," +
                        "-0.034707338554096}),\n" +
                        "        \t\tnew ArrayRealVector(new double[] {0.855205995537564,0.327134656629775," +
                        "-0.265382397060548,0.282690729026706,0.105736068025572,-0.009138126622039," +
                        "0.000367751821196}),\n" +
                        "        \t\tnew ArrayRealVector(new double[] {-0.002913069901144,-0.005177515777101," +
                        "0.041906334478672,-0.109315918416258,0.436192305456741,0.026307315639535,0.891797507436344})" +
                        ",\n" +
                        "        \t\tnew ArrayRealVector(new double[] {-0.005738311176435,-0.010207611670378," +
                        "0.082662420517928,-0.215733886094368,0.861606487840411,-0.025478530652759," +
                        "-0.451080697503958})\n" +
                        "        };\n" +
                        "\n" +
                        "        // the following line triggers the exception\n" +
                        "        EigenDecomposition decomposition =\n" +
                        "            new EigenDecompositionImpl(mainTridiagonal, secondaryTridiagonal, MathUtils" +
                        ".SAFE_MIN);\n" +
                        "\n" +
                        "        double[] eigenValues = decomposition.getRealEigenvalues();\n" +
                        "        for (int i = 0; i &lt; refEigenValues.length; ++i) {\n" +
                        "            assertEquals(refEigenValues[i], eigenValues[i], 1.0e-3);\n" +
                        "            if (refEigenVectors[i].dotProduct(decomposition.getEigenvector(i)) &lt; 0) {\n" +
                        "                assertEquals(0, refEigenVectors[i].add(decomposition.getEigenvector(i))" +
                        ".getNorm(), 1.0e-5);\n" +
                        "            } else {\n" +
                        "                assertEquals(0, refEigenVectors[i].subtract(decomposition.getEigenvector(i))" +
                        ".getNorm(), 1.0e-5);\n" +
                        "            }\n" +
                        "        }\n" +
                        "\n" +
                        "    }\n" +
                        "{code}";

        final String taggedText = StormedClient.tagText(codeToParse);
        System.out.println(taggedText);

    }
}