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

public class RobotContainer {
    private final SwerveModuleSubsystem mModule = new SwerveModuleSubsystem();
    private final XboxController mController = new XboxController(0);
    private final ManualControl mManualControl = new ManualControl(mModule, mController);

    public RobotContainer() {
        //configureButtonBindings();

        mModule.setDefaultCommand(mManualControl);
    }
}
