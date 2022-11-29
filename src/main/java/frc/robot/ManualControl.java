public class ManualControl extends CommandBase {
    private final XboxController mController;
    private final SwerveModuleSubsystem mModule;

    @param subsystem

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