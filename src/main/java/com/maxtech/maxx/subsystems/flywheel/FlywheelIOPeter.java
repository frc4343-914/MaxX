package com.maxtech.maxx.subsystems.flywheel;

import com.revrobotics.CANSparkMax;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;

public class FlywheelIOPeter implements FlywheelIO {
    private final CANSparkMax master = new CANSparkMax(5, kBrushless);
    private final CANSparkMax slave = new CANSparkMax(6, kBrushless);

    public FlywheelIOPeter() {
        master.setInverted(true);
        slave.follow(master, true);

        master.getPIDController().setP(1);
        master.getPIDController().setI(0);
        master.getPIDController().setD(0);
    }

    @Override
    public void setVoltage(double voltage) {
        master.set(voltage / 12);
    }

    @Override
    public void setVelocity(double velocity) {
        if (velocity == 0) master.set(0);
        master.set((velocity / 600));
    }

    @Override
    public double getVelocity() {
        return master.getEncoder().getVelocity();
    }

    @Override
    public double getVoltage() {
        return master.getBusVoltage();
    }

    @Override
    public double getPercentOut() {
        return 0;
    }

    @Override
    public double getCurrent() {
        return 0;
    }
}
