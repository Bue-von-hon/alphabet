package uhs.alphabet.domain.badge;

public class StuBadge {
    private final String preBadge = "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:svgjs=\"http://svgjs.com/svgjs\" width=\"353\" height=\"134\">\n" +
            "  <style>\n" +
            "    .pen {\n" +
            "      fill: none;\n" +
            "      stroke: url(#rainbow);\n" +
            "      stroke-width: 2;\n" +
            "      stroke-linecap: round;\n" +
            "    }\n" +
            "    .p{\n" +
            "      stroke-dasharray: 140 140;\n" +
            "      stroke-dashoffset: 140;\n" +
            "      animation-duration: 1s;\n" +
            "      animation-delay: 4s;\n" +
            "      animation-name: draw;\n" +
            "      animation-direction: alternate;\n" +
            "      animation-timing-function: linear;\n" +
            "      animation-fill-mode: forwards;\n" +
            "    }\n" +
            "    .b{\n" +
            "      stroke-dasharray: 280 280;\n" +
            "      stroke-dashoffset: 280;\n" +
            "      animation-duration: 2s;\n" +
            "      animation-delay: 5.5s;\n" +
            "      animation-name: draw;\n" +
            "      animation-direction: alternate;\n" +
            "      animation-timing-function: linear;\n" +
            "      animation-fill-mode: forwards;\n" +
            "    }\n" +
            "    @keyframes draw {\n" +
            "      to {\n" +
            "        stroke-dashoffset: 0;\n" +
            "      }\n" +
            "    }\n" +
            "  </style>\n" +
            "  <defs>\n" +
            "    <linearGradient id=\"rainbow\" x1=\"60%\" x2=\"85%\" y1=\"0\" y2=\"0\" gradientUnits=\"userSpaceOnUse\" >\n" +
            "      <stop stop-color=\"#FF0000\" offset=\"0%\"/>\n" +
            "      <stop stop-color=\"#FF9900\" offset=\"33%\"/>\n" +
            "      <stop stop-color=\"#CC33FF\" offset=\"66%\"/>\n" +
            "      <stop stop-color=\"#ff54dd\" offset=\"100%\"/> \n" +
            "    </linearGradient>\n" +
            "  </defs>\n" +
            "  <rect width=\"350\" height=\"131\" fill=\"white\" stroke=\"#111111\" rx=\"20\" ry=\"20\" stroke-width=\"3\" x=\"1\" y=\"1\"></rect>\n" +
            "<g>\n" +
            "\t<rect width=\"30\" height=\"40\" fill=\"#c4e693\" stroke-width=\"2\" stroke=\"#111111\" x=\"10\" y=\"15\" opacity=\"0\">\n" +
            "\t  <animate id=\"animation1\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.4s\" fill=\"freeze\"/>\n" +
            "    <animate attributeName=\"width\" values=\"130;30\" dur=\"0.4s\"/>\n" +
            "    <animate attributeName=\"height\" values=\"100;40\" dur=\"0.4s\"/>\n" +
            "\t</rect>\n" +
            "\t<rect width=\"60\" height=\"40\" fill=\"#ffc519\" stroke-width=\"2\" stroke=\"#111111\" x=\"100\" y=\"15\" opacity=\"0\">\n" +
            "\t  <animate id=\"animation2\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.4s\" fill=\"freeze\" begin=\"animation1.end\"/>\n" +
            "    <animate attributeName=\"width\" values=\"0;60\" dur=\"0.4s\" begin=\"animation1.end\"/>\n" +
            "    <animate attributeName=\"height\" values=\"0;40\" dur=\"0.4s\" begin=\"animation1.end\"/>\n" +
            "\t</rect>\n" +
            "\t<rect width=\"30\" height=\"60\" fill=\"#fffa78\" stroke-width=\"2\" stroke=\"#111111\" x=\"40\" y=\"35\" opacity=\"0\">\n" +
            "\t  <animate id=\"animation3\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.4s\" fill=\"freeze\" begin=\"animation2.end\"/>\n" +
            "    <animate attributeName=\"width\" values=\"130;30\" dur=\"0.4s\" begin=\"animation2.end\"/>\n" +
            "    <animate attributeName=\"height\" values=\"100;60\" dur=\"0.4s\" begin=\"animation2.end\"/>\n" +
            "\t</rect>\n" +
            "\t<rect width=\"90\" height=\"20\" fill=\"#3cfbff\" stroke-width=\"2\" stroke=\"#111111\" x=\"10\" y=\"95\" opacity=\"0\">\n" +
            "\t  <animate id=\"animation4\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.4s\" fill=\"freeze\" begin=\"animation3.end\"/>\n" +
            "    <animate attributeName=\"x\" values=\"0;10\" dur=\"0.4s\" begin=\"animation3.end\"/>\n" +
            "    <animate attributeName=\"y\" values=\"0;95\" dur=\"0.4s\" begin=\"animation3.end\"/>\n" +
            "    <animate attributeName=\"width\" values=\"130;90\" dur=\"0.4s\" begin=\"animation3.end\"/>\n" +
            "    <animate attributeName=\"height\" values=\"100;20\" dur=\"0.4s\" begin=\"animation3.end\"/>\n" +
            "\t</rect>\n" +
            "\t<rect width=\"30\" height=\"40\" fill=\"#111111\" stroke-width=\"2\" stroke=\"#111111\" x=\"70\" y=\"15\" opacity=\"0\">\n" +
            "\t  <animate id=\"animation5\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.4s\" fill=\"freeze\" begin=\"animation4.end\"/>\n" +
            "    <animate attributeName=\"x\" values=\"0;70\" dur=\"0.4s\" begin=\"animation4.end\"/>\n" +
            "    <animate attributeName=\"y\" values=\"0;15\" dur=\"0.4s\" begin=\"animation4.end\"/>\n" +
            "    <animate attributeName=\"width\" values=\"130;30\" dur=\"0.4s\" begin=\"animation4.end\"/>\n" +
            "    <animate attributeName=\"height\" values=\"100;40\" dur=\"0.4s\" begin=\"animation4.end\"/>\n" +
            "\t</rect>\n" +
            "\t<rect width=\"30\" height=\"40\" fill=\"#ff5675\" stroke-width=\"2\" stroke=\"#111111\" x=\"130\" y=\"55\" opacity=\"0\">\n" +
            "\t  <animate id=\"animation6\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.4\" fill=\"freeze\" begin=\"animation5.end\"/>\n" +
            "    <animate attributeName=\"x\" values=\"0;130\" dur=\"0.4s\" begin=\"animation5.end\"/>\n" +
            "    <animate attributeName=\"y\" values=\"0;55\" dur=\"0.4s\" begin=\"animation5.end\"/>\n" +
            "    <animate attributeName=\"width\" values=\"130;30\" dur=\"0.4s\" begin=\"animation5.end\"/>\n" +
            "    <animate attributeName=\"height\" values=\"100;40\" dur=\"0.4s\" begin=\"animation5.end\"/>\n" +
            "\t</rect>\n" +
            "\t<rect width=\"30\" height=\"20\" fill=\"#c4e693\" stroke-width=\"2\" stroke=\"#111111\" x=\"100\" y=\"95\" opacity=\"0\">\n" +
            "\t  <animate id=\"animation7\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.4s\" fill=\"freeze\" begin=\"animation6.end\"/>\n" +
            "    <animate attributeName=\"x\" values=\"0;100\" dur=\"0.4s\" begin=\"animation6.end\"/>\n" +
            "    <animate attributeName=\"y\" values=\"0;95\" dur=\"0.4s\" begin=\"animation6.end\"/>\n" +
            "    <animate attributeName=\"width\" values=\"130;30\" dur=\"0.4s\" begin=\"animation6.end\"/>\n" +
            "    <animate attributeName=\"height\" values=\"100;20\" dur=\"0.4s\" begin=\"animation6.end\"/>\n" +
            "\t</rect>\n" +
            "<!-- \n" +
            "    blank\n" +
            "-->\n" +
            "  <rect width=\"30\" height=\"20\" fill=\"none\" stroke-width=\"2\" stroke=\"#111111\" x=\"40\" y=\"15\" opacity=\"0\">\n" +
            "\t  <animate id=\"animation8\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.5s\" fill=\"freeze\" begin=\"animation7.end\"/>\n" +
            "\t</rect>\n" +
            "\t<rect width=\"30\" height=\"40\" fill=\"none\" stroke-width=\"2\" stroke=\"#111111\" x=\"10\" y=\"55\" opacity=\"0\">\n" +
            "\t  <animate id=\"animation9\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.5s\" fill=\"freeze\" begin=\"animation7.end\"/>\n" +
            "\t</rect>\n" +
            "\t<rect width=\"60\" height=\"40\" fill=\"none\" stroke-width=\"2\" stroke=\"#111111\" x=\"70\" y=\"55\" opacity=\"0\">\n" +
            "\t  <animate id=\"animation10\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.5s\" fill=\"freeze\" begin=\"animation7.end\"/>\n" +
            "\t</rect>\n" +
            "\t<rect width=\"30\" height=\"20\" fill=\"none\" stroke-width=\"2\" stroke=\"#111111\" x=\"130\" y=\"95\" opacity=\"0\">\n" +
            "\t  <animate id=\"animation11\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.5s\" fill=\"freeze\" begin=\"animation7.end\"/>\n" +
            "\t</rect>\n" +
            "</g>\n" +
            "  <!--A-->\n" +
            "  <line x1=\"217\" y1=\"21\" x2=\"217\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line1\" attributeName=\"x2\" values=\"217 ; 210\" dur=\"0.2s\" fill=\"freeze\"  begin=\"animation7.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\"  begin=\"animation7.end\"/>\n" +
            "    <animate attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\"  begin=\"animation7.end\"/>\n" +
            "  </line>\n" +
            "  <line x1=\"217\" y1=\"21\" x2=\"217\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line2\" attributeName=\"x2\" values=\"217 ; 224\" dur=\"0.2s\" fill=\"freeze\" begin=\"line1.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line1.end\"/>\n" +
            "    <animate attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line1.end\"/>\n" +
            "  </line>\n" +
            "  <line x1=\"212\" y1=\"32\" x2=\"212\" y2=\"32\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line3\" attributeName=\"x2\" values=\"212 ; 221\" dur=\"0.2s\" fill=\"freeze\" begin=\"line2.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line2.end\"/>\n" +
            "  </line>\n" +
            "  <!--L-->\n" +
            "  <line x1=\"227\" y1=\"21\" x2=\"227\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line4\" attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line3.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line3.end\"/>\n" +
            "  </line>\n" +
            "  <line x1=\"227\" y1=\"37\" x2=\"227\" y2=\"37\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line5\" attributeName=\"x2\" values=\"227 ; 234\" dur=\"0.2s\" fill=\"freeze\" begin=\"line4.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line4.end\"/>\n" +
            "  </line>\n" +
            "  <!--P-->\n" +
            "  <line x1=\"237\" y1=\"21\" x2=\"237\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line6\" attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line5.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line5.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line5.end\"/>\n" +
            "  </line>\n" +
            "  <path d=\"M237 21 C247 21 247 30 237 30\" class=\"pen p\"></path>\n" +
            "  <!--H-->\n" +
            "  <line x1=\"247\" y1=\"21\" x2=\"247\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line8\" attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line6.end+0.2\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line6.end+0.2\"/>\n" +
            "  </line>\n" +
            "  <line x1=\"247\" y1=\"30\" x2=\"247\" y2=\"30\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line9\" attributeName=\"x2\" values=\"247 ; 256\" dur=\"0.2s\" fill=\"freeze\" begin=\"line8.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line8.end\"/>\n" +
            "  </line>\n" +
            "  <line x1=\"256\" y1=\"21\" x2=\"256\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line10\" attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line9.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line9.end\"/>\n" +
            "  </line>\n" +
            "  <!--A-->\n" +
            "  <line x1=\"267\" y1=\"21\" x2=\"267\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line11\" attributeName=\"x2\" values=\"267 ; 260\" dur=\"0.2s\" fill=\"freeze\" begin=\"line10.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line10.end\"/>\n" +
            "    <animate attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line10.end\"/>\n" +
            "  </line>\n" +
            "  <line x1=\"267\" y1=\"21\" x2=\"267\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line12\" attributeName=\"x2\" values=\"267 ; 274\" dur=\"0.2s\" fill=\"freeze\" begin=\"line11.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line11.end\"/>\n" +
            "    <animate attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line11.end\"/>\n" +
            "  </line>\n" +
            "  <line x1=\"262\" y1=\"32\" x2=\"262\" y2=\"32\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line13\" attributeName=\"x2\" values=\"262 ; 271\" dur=\"0.2s\" fill=\"freeze\" begin=\"line12.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line12.end\"/>\n" +
            "  </line>\n" +
            "  <!--B-->\n" +
            "  <line x1=\"277\" y1=\"21\" x2=\"277\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line14\" attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line13.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line13.end\"/>\n" +
            "  </line>\n" +
            "  <path d=\"M277 21 C287 21 287 29 277 29 287 29 287 37 277 37\" class=\"pen b\"></path>\n" +
            "  <!--E-->\n" +
            "  <line x1=\"287\" y1=\"21\" x2=\"287\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line16\" attributeName=\"x2\" values=\"287 ; 294\" dur=\"0.2s\" fill=\"freeze\" begin=\"line14.end+0.2\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line14.end+0.2\"/>\n" +
            "  </line>\n" +
            "  <line x1=\"287\" y1=\"21\" x2=\"287\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line17\" attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line16.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line16.end\"/>\n" +
            "  </line>\n" +
            "  <line x1=\"287\" y1=\"37\" x2=\"287\" y2=\"37\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line18\" attributeName=\"x2\" values=\"287 ; 294\" dur=\"0.2s\" fill=\"freeze\" begin=\"line17.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line17.end\"/>\n" +
            "  </line>\n" +
            "  <line x1=\"287\" y1=\"29\" x2=\"287\" y2=\"29\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line19\" attributeName=\"x2\" values=\"287 ; 294\" dur=\"0.2s\" fill=\"freeze\" begin=\"line18.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line18.end\"/>\n" +
            "  </line>\n" +
            "  <!--T-->\n" +
            "  <line x1=\"297\" y1=\"21\" x2=\"297\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line20\" attributeName=\"x2\" values=\"297 ; 306\" dur=\"0.2s\" fill=\"freeze\" begin=\"line19.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line19.end\"/>\n" +
            "  </line>\n" +
            "  <line x1=\"302\" y1=\"21\" x2=\"302\" y2=\"21\" class=\"pen\" opacity=\"0\">\n" +
            "    <animate id=\"line21\" attributeName=\"y2\" values=\"21 ; 37\" dur=\"0.2s\" fill=\"freeze\" begin=\"line20.end\"/>\n" +
            "    <animate attributeName=\"opacity\" values=\"0;1\" dur=\"0.2s\" fill=\"freeze\" begin=\"line20.end\"/>\n" +
            "  </line>\n" +
            "<!-- \n" +
            "    text \n" +
            "-->\n" +
            "  <text font-size=\"20\" x=\"210\" y=\"40\" opacity=\"0\">\n" +
            "\t  <tspan id=\"name\" dy=\"26\" x=\"210.0109466053608\">";
    private final String midBadge = "</tspan>\n" +
            "    <animate id=\"animation14\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.5s\" fill=\"freeze\" begin=\"line21.end\"/>\n" +
            "  </text>\n" +
            "  <text font-size=\"20\" x=\"210\" y=\"60\" opacity=\"0\">\n" +
            "\t  <tspan id=\"handle\" dy=\"26\" x=\"210.0109466053608\">";
    private final String postBadge = "</tspan>\n" +
            "    <animate id=\"animation13\" attributeName=\"opacity\" values=\"0;1\" dur=\"0.5s\" fill=\"freeze\" begin=\"line21.end\"/>\n" +
            "  </text>\n" +
            "</svg>";
    private final String name;
    private final String handle;
    private final String badge;

    public StuBadge(final String name, final String handle) {
        this.name = name;
        this.handle = handle;
        badge = preBadge+name+midBadge+handle+postBadge;
    }

    public String getBadge() {
        return badge;
    }
}
