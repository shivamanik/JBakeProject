package org.jbake.launcher;

import java.io.File;
import java.io.StringWriter;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.jbake.api.util.ConfigUtil;
import org.jbake.api.util.FileUtil;
import org.jbake.app.Oven;
import org.jbake.service.plugin.JBakePluginServiceFactory;

/**
 * Launcher for JBake.
 *
 * @author Jonathan Bullock <jonbullock@gmail.com>
 *
 */
public class Main {
//	public static final String VERSION = "v2.2";

    private final String USAGE_PREFIX = "Usage: jbake";

    /**
     * Runs the app with the given arguments.
     *
     * @param String[] args
     */
    public static void main(String[] args) {
        Main m = new Main();
        m.run(m.parseArguments(args));
    }

    private void run(LaunchOptions options) {
        try {
            Oven oven = new Oven(options.getSource(), options.getDestination());
            oven.setupPaths();
            oven.bake();
        } catch (Exception e) {
//			System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private LaunchOptions parseArguments(String[] args) {
        LaunchOptions res = new LaunchOptions();
        CmdLineParser parser = new CmdLineParser(res);

        try {
            parser.parseArgument(args);

            CompositeConfiguration config = null;
            try {
                config = ConfigUtil.load(res.getSource());
            } catch (ConfigurationException e) {
                e.printStackTrace();
                System.exit(1);
            }

            System.out.println("JBake " + config.getString("version") + " (" + config.getString("build.timestamp") + ") [http://jbake.org]");
            System.out.println();

            if (res.isListPlugin()) {
                listAllPlugins();
            }

            if (res.isHelpNeeded()) {
                printUsage(parser);
            }

            if (res.isRunServer()) {
                if (res.getSource().getPath().equals(".")) {
                    // use the default destination folder
                    runServer(config.getString("destination.folder"), config.getString("server.port"));
                } else {
                    runServer(res.getSource().getPath(), config.getString("server.port"));
                }
            }

            if (res.isInit()) {
                initStructure(config);
            }
        } catch (CmdLineException e) {
            printUsage(parser);
        }

        return res;
    }

    private void printUsage(CmdLineParser parser) {
        StringWriter sw = new StringWriter();
        sw.append(USAGE_PREFIX);
        parser.printSingleLineUsage(sw, null);
        System.out.println(sw.toString());
        parser.setUsageWidth(100);
        parser.printUsage(System.out);
        System.exit(0);
    }

    private void runServer(String path, String port) {
        JettyServer.run(path, port);
        System.exit(0);
    }

    private void initStructure(CompositeConfiguration config) {
        Init init = new Init(config);
        try {
            File codeFolder = FileUtil.getRunningLocation();
            init.run(new File("."), codeFolder);
            System.out.println("Base folder structure successfully created.");
            System.exit(0);
        } catch (Exception e) {
            System.err.println("Failed to initalise structure!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void listAllPlugins() {
        System.out.println("Installed Plugins:");
        System.out.println("********************");
        JBakePluginServiceFactory.getDefaultPluginService().listPlugins();
        System.exit(0);
    }
}
