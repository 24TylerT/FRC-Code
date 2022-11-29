public class RobotContainer {
    private final SwerveModuleSubsystem mModule = new SwerveModuleSubsystem();
    private final XboxController mController = new XboxController(0);
    private final ManualControl mManualControl = new ManualControl(mModule, mController);

    public RobotContainer() {
        configureButtonBindings();

        mModule.setDefaultCommand(mManualControl);
    }
}
