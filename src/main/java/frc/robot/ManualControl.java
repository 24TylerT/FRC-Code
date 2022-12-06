package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.math.controller.PIDController;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.*;

import frc.robot.Constants;
import frc.robot.Constants.SwerveConstants;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ManualControl extends CommandBase {
    private final XboxController mController;
    private final SwerveModuleSubsystem mModule;


    public ManualControl(SwerveModuleSubsystem module, XboxController controller){
        mController = controller;
        mModule = module;

        addRequirements(mModule);
    }

    @Override
    public void execute() {
        mModule.getModule().jogThrottle(mController.getLeftY());
    }
}