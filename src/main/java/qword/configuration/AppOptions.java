package qword.configuration;


import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

public class AppOptions extends OptionsBase {

    @Option(
            name = "help",
            abbrev = 'h',
            help = "Prints usage info.",
            defaultValue = "false"
    )
    public boolean help;

    @Option(
            name = "player",
            abbrev = 'p',
            help = "Display basic player info.",
            defaultValue = "-1"
    )
    public int player;

    @Option(
            name = "matches",
            abbrev = 'm',
            help = "Display player's matches history.",
            defaultValue = "-1"
    )
    public int matches;

    @Option(
            name = "ranking",
            abbrev = 'r',
            help = "Display players ranking.",
            defaultValue = "false"
    )
    public boolean ranking;

    @Option(
            name = "wins-losses",
            abbrev = 'w',
            help = "Display players sorted by wins/losses.",
            defaultValue = "false"
    )
    public boolean winsLosses;
}
