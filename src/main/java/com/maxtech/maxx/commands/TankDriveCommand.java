package com.maxtech.maxx.commands;

import com.maxtech.maxx.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TankDriveCommand extends CommandBase {
    private final double ls;
    private final double rs;
    private final DriveSubsystem drivetrain;

    public TankDriveCommand(double ls, double rs, DriveSubsystem drivetrain) {
        this.ls = ls;
        this.rs = rs;
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.tank(ls, rs);

    }
}