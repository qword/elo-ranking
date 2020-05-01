package qword;

import com.google.devtools.common.options.OptionsParser;
import qword.configuration.AppOptions;
import qword.configuration.DiConfig;
import qword.services.PresentationService;

import java.util.Map;

import static com.google.devtools.common.options.OptionsParser.HelpVerbosity.LONG;

public class EloRankingApp {

    public static void main(String[] args) {
        final OptionsParser parser = OptionsParser.newOptionsParser(AppOptions.class);
        parser.parseAndExitUponError(args);
        final AppOptions options = parser.getOptions(AppOptions.class);

        if (options.help) {
            printUsage(parser);
            System.exit(0);
        }

        final DiConfig diConfig = new DiConfig();
        diConfig.getReaderService().loadData();

        final PresentationService presentationService = diConfig.getPresentationService();

        if (options.player > -1) {
            presentationService.showPlayer(options.player);
        } else if (options.matches > -1) {
            presentationService.showPlayerHistory(options.matches);
        } else if (options.ranking) {
            presentationService.showPlayersByRanking();
        } else if (options.winsLosses) {
            presentationService.showPlayersByWinsAndLosses();
        } else {
            printUsage(parser);
        }
    }

    private static void printUsage(final OptionsParser parser) {
        System.out.println("Usage: java -jar app.jar OPTIONS");
        System.out.println(parser.describeOptions(Map.of(), LONG));
    }
}
