package com.maxtech.maxx.subsystems.drivetrain;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.wrappers.rev.CANSparkMax;
import com.maxtech.maxx.RobotContainer;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static com.maxtech.maxx.RobotContainer.decideIO;

public class Drive extends Subsystem {
    private static Drive instance;

    private DriveIO io = decideIO(DriveIOMax.class, DriveIOMax.class);

    private static final RobotLogger logger = RobotLogger.getInstance();
    private boolean inverted = false;

    public static Drive getInstance() {
        if (instance == null) {
            instance = new Drive();
        }

        return instance;
    }

    private Drive() {
        logger.log("Chose %s for I/O", io.getClass().getName());
    }

    @Override
    public void periodic() {
        io.periodic();
    }

    @Override
    public void simulationPeriodic() {
        io.simulationPeriodic();
    }

    public void arcade(double s, double r) {
        if (!inverted) {
            io.arcade(s, r);
        } else {
            io.arcade(-s, -r);
        }
    }

    public void tank(double ls, double rs) {
        if (!inverted) {
            io.tank(ls, rs);
        } else {
            io.tank(-ls, -rs);
        }
    }

    public void resetOdometry(Pose2d pose) {
        io.resetOdometry(pose);
    }

    public Pose2d getPose() {
        return io.getPose();
    }

    public double getDistanceTravelled(CANSparkMax controller, double gearing, double wheelDiameter) {
        return io.getDistanceTravelled(controller, gearing, wheelDiameter);
    }

    public double getDistanceTravelled(CANSparkMax controller) {
        return io.getDistanceTravelled(controller);
    }

    public double getDistanceTravelled() {
        return io.getDistanceTravelled();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return io.getWheelSpeeds();
    }

    public void toggleDirection() {
        inverted = !inverted;
    }

    @Override
    public void sendTelemetry(String prefix) {
        SmartDashboard.putData(prefix + "field", io.getField());
        SmartDashboard.putNumber(prefix + "x", getPose().getX());
        SmartDashboard.putNumber(prefix + "y", getPose().getY());
    }
}
