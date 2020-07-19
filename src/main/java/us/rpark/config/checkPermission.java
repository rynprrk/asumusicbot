package us.rpark.config;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class checkPermission {

    public static boolean isStaff(CommandEvent event) { // Checks to see if a member is staff
        if (event.getMember().hasPermission(Permission.KICK_MEMBERS)) { // Checks for KICK_MEMBERS permission specifically
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSupporter(CommandEvent event) throws IOException {
        Config config = new Config(new File("./config.json")); // New Config instance
        Role supporter = event.getGuild().getRoleById(config.getString("supporterId")); // References config.json to get a supporter role id
        List<Role> userRoles = event.getGuild().getMember(event.getAuthor()).getRoles(); // Creates a list of the sending user's roles

        // If the user has supporter role, return true
        if (userRoles.contains(supporter)) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean isAdmin(CommandEvent event) {
        // If the user has permission ADMINISTRATOR, return true
        if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            return true;
        } else {
            return false;
        }
    }
}
