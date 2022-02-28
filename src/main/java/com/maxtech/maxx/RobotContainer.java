package com.maxtech.maxx;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.commands.NextLEDPattern;
import com.maxtech.maxx.commands.flywheel.SetFlywheel;
import com.maxtech.maxx.subsystems.Intake;
import com.maxtech.maxx.subsystems.LEDs;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import java.util.List;

/**
 * The bulk connector for our robot. This class unifies subsystems, commands, and button bindings under one place. This
 * is then called from {@link Robot}.
 */
public class RobotContainer {
    public static final int teamNumber = 4343;

    private final RobotLogger logger = RobotLogger.getInstance();

    /**
     * A handle to an Xbox controller on port 0.
     */
    private final XboxController masterController = new XboxController(0);

    private final Drive drivetrain = Drive.getInstance();
    private final Flywheel flywheel = Flywheel.getInstance();
    private final Indexer indexer = Indexer.getInstance();
    private final Intake intake = Intake.getInstance();
    private final LEDs leds = LEDs.getInstance();

    public RobotContainer() {
        // Configure the button bindings.
        configureButtonBindings();
    }

    /**
     * Our global button -> command mappings.
     * <p>
     * More documentation on how this is achieved at https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html
     */
    private void configureButtonBindings() {
        // We set the default command for the drivetrain to arcade driving based on the controller values.
        drivetrain.setDefaultCommand(new RunCommand(() -> {
            double speed = masterController.getRightTriggerAxis() - masterController.getLeftTriggerAxis();
            double rotation = masterController.getLeftX();

            drivetrain.arcade(speed, rotation);
        }, drivetrain));

        // TODO: review this method of binding commands to methods. It's almost certainly too verbose.
        new JoystickButton(masterController, XboxController.Button.kLeftBumper.value).whenPressed(new NextLEDPattern());
    }

    /**
     * This method is used to get the command for autonomous, used in {@link Robot}.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // return new TrackBall();
        // return new ExamplePath();
        return new SetFlywheel(1000);
    }

    /** All of these subsystems send telemetry. */
    public List<Subsystem> getTelemetrySubsystems() {
        return List.of(drivetrain, flywheel, intake);
    }
}
