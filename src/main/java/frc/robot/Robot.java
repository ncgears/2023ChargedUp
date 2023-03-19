// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**
 * This Robot Base has been written and provided by Jim Barstow <jim@ncgears.com>
 * Courtesy of FRC Team 1918, NC GEARS
 * 
 * NC GEARS maintains this Robot Base each season to support many teams that need additional code support.
 * It may be freely copied and used. If you find it helpful, please send Jim an email saying thanks
 * or to share ideas for improvement.
 * 
 * TODO: Before deploying this code, you should set your team number.
 *  1. In VS Code, press CTRL-SHIFT-P
 *  2. Type "Set Team Number" and choose "WPILib: Set Team Number"
 *  3. Enter your team number and press enter
 * 
 * TODO: It is recommended to set dashboard type to "Shuffleboard" in drivers station settings section
 * The layout can be loaded in shuffleboard in the file->load layout menu
 * There is a basic layout in shuffleboard\shuffleboard.json, but the robot will build a "Robot" tab automatically
 * with an auton chooser and a camera (if you have a usb cam and hasCamera set to true in constants)
 */
 
package frc.robot;

/* Uncomment this if you have a NavX on the Robo Rio SPI port (safe to leave always, even if you dont have one) */
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;

/* TODO: Uncomment this if you have a USB Camera connected to the Robo Rio */
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;

/* TODO: Uncomment this if drivetrain uses TalonSRX and/or VictorSPX */
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

/* TODO: Uncomment this if drivetrain uses CANSparkMax */
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/* TODO: Uncomment this if there is Pneumatics */
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/* The following should always be imported */
import edu.wpi.first.wpilibj.Timer; //Used for sequencing Autons
import edu.wpi.first.wpilibj.Joystick; //Used for setting up controller
import edu.wpi.first.wpilibj.TimedRobot; //Used for the robot
import edu.wpi.first.wpilibj.drive.DifferentialDrive; //Used for a differential drivetrain (tank)
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup; //Used to group motor controllers for simpler control
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Map;

// Import some utility classes
import frc.robot.utils.OI;
import frc.robot.utils.Logger;

/**
 * This robot base builds a differential drive (left/right side, such as the 6-wheel Andymark kit chassis).
 * The control system is Arcade steering, where one control stick is the throttle, and the other is the turning.
 * This control system works well with a dual thumbstick controller, such as logitech F310 or xbox controller.
 */
public class Robot extends TimedRobot {

  /**
   *  TODO: Uncomment the appropriate motor controllers for your drivetrain
   *  You must edit Constants.java and set the appropriate IDs for your controllers.
  */

  /* This sets up a 4 controller drivetrain using 2 Talons per side */
  // private final WPI_TalonSRX m_driveLeftFront = new WPI_TalonSRX(Constants.IDs.DriveTrain.driveLeftFront);
  // private final WPI_TalonSRX m_driveLeftRear = new WPI_TalonSRX(Constants.IDs.DriveTrain.driveLeftRear);
  // private final WPI_TalonSRX m_driveRightFront = new WPI_TalonSRX(Constants.IDs.DriveTrain.driveRightFront);
  // private final WPI_TalonSRX m_driveRightRear = new WPI_TalonSRX(Constants.IDs.DriveTrain.driveRightRear);

  /* This sets up a 4 controller drivetrain using 2 Victors per side */
  // private final WPI_VictorSPX m_driveLeftFront = new WPI_VictorSPX(Constants.IDs.DriveTrain.driveLeftFront);
  // private final WPI_VictorSPX m_driveLeftRear = new WPI_VictorSPX(Constants.IDs.DriveTrain.driveLeftRear);
  // private final WPI_VictorSPX m_driveRightFront = new WPI_VictorSPX(Constants.IDs.DriveTrain.driveRightFront);
  // private final WPI_VictorSPX m_driveRightRear = new WPI_VictorSPX(Constants.IDs.DriveTrain.driveRightRear);

  /* This sets up a 4 controller drivetrain using 2 SparkMax per side */
  // The motor type must be configured as either MotorType.kBrushed (CIM) or MotorType.kBrushless (Neo)
  private final CANSparkMax m_driveLeftFront = new CANSparkMax(Constants.IDs.DriveTrain.driveLeftFront, MotorType.kBrushed);
  private final CANSparkMax m_driveLeftRear = new CANSparkMax(Constants.IDs.DriveTrain.driveLeftRear, MotorType.kBrushed);
  private final CANSparkMax m_driveRightFront = new CANSparkMax(Constants.IDs.DriveTrain.driveRightFront, MotorType.kBrushed);
  private final CANSparkMax m_driveRightRear = new CANSparkMax(Constants.IDs.DriveTrain.driveRightRear, MotorType.kBrushed);

  /* This builds 2 motor control groups to make it easier to control the motor controllers */
  private final MotorControllerGroup m_leftDriveMotors = new MotorControllerGroup(m_driveLeftFront, m_driveLeftRear);
  private final MotorControllerGroup m_rightDriveMotors = new MotorControllerGroup(m_driveRightFront, m_driveRightRear);

  /* This builds the differential drive base */
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftDriveMotors, m_rightDriveMotors);

  /* This builds your operator interface (Joysticks) */
  private final Joystick m_driver = new Joystick(Constants.IDs.OI.driverJoy);
  private final Joystick m_operator = new Joystick(Constants.IDs.OI.operJoy);

  /* This sets up the NavX gyro */
  private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

  /* This sets up a timer used for various things */
  private final Timer m_timer = new Timer();

  /* This creates the Auton chooser */
  private String m_autonSelected;
  private final SendableChooser<String> m_autonChooser = new SendableChooser<>();

  /* This sets up some example pneumatic devices */
  private final DoubleSolenoid m_gearShift = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.IDs.Solenoid.driveLowGear, Constants.IDs.Solenoid.driveHighGear);
  private final Solenoid m_intake = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.IDs.Solenoid.intakeDeploy);

  /* This sets up an arm using 2 talons */
  // private final WPI_TalonSRX m_armLeft = new WPI_TalonSRX(Constants.IDs.Arm.armLeft);
  // private final WPI_TalonSRX m_armRight = new WPI_TalonSRX(Constants.IDs.Arm.armRight);
  // private final MotorControllerGroup m_arm = new MotorControllerGroup(m_armLeft, m_armRight);

  @Override
  public void robotInit() {
    //TODO: Uncomment this if using talons or victors for drivetrain
    /* It is a good idea to reset and configure them */
    // m_driveLeftFront.configFactoryDefault();
    // m_driveLeftFront.setNeutralMode(NeutralMode.Coast);
    // m_driveLeftRear.configFactoryDefault();
    // m_driveLeftRear.setNeutralMode(NeutralMode.Coast);
    // m_driveRightFront.configFactoryDefault();
    // m_driveRightFront.setNeutralMode(NeutralMode.Coast);
    // m_driveRightRear.configFactoryDefault();
    // m_driveRightRear.setNeutralMode(NeutralMode.Coast);

    //TODO: Uncomment this if using sparkmax for drivetrain
    /* It is a good idea to reset and configure them */
    m_driveLeftFront.restoreFactoryDefaults();
    m_driveLeftFront.setIdleMode(IdleMode.kCoast);
    m_driveLeftRear.restoreFactoryDefaults();
    m_driveLeftRear.setIdleMode(IdleMode.kCoast);
    m_driveRightFront.restoreFactoryDefaults();
    m_driveRightFront.setIdleMode(IdleMode.kCoast);
    m_driveRightRear.restoreFactoryDefaults();
    m_driveRightRear.setIdleMode(IdleMode.kCoast);

    //Configure the drivetrains (invert sides as defined in constants)
    m_leftDriveMotors.setInverted(Constants.DriveTrain.Left.isInverted);
    m_rightDriveMotors.setInverted(Constants.DriveTrain.Right.isInverted);
    
    //Configure the solenoids
    m_gearShift.set(DoubleSolenoid.Value.kForward); //high gear
    m_intake.set(!Constants.Intake.kAirStateDeployed); //retracted

    //Add the autons to the chooser
    //The first argument is the friendly name displayed in the dashboard
    //The second argument is the auton object name that must match in autonomousPeriodic section
    m_autonChooser.setDefaultOption("Do Nothing","autoDoNothing"); 
    m_autonChooser.addOption("Drive Forward 2sec","autoDriveForward");
    m_autonChooser.addOption("Drive Backwards 2sec","autoDriveBackward");
    //put the chooser in the dashboard
    SmartDashboard.putData("Auton Choices",m_autonChooser);

    ShuffleboardTab robotTab = Shuffleboard.getTab("Robot");
    robotTab.add("Auton Chooser", m_autonChooser)
      .withPosition(0,0)
      .withSize(3,1)
      .withWidget(BuiltInWidgets.kComboBoxChooser);
    if(Constants.Global.hasCamera) {
      UsbCamera m_camera = CameraServer.startAutomaticCapture();
      m_camera.setResolution(320, 240);
      m_camera.setFPS(15);
      robotTab.add("USB Camera", m_camera)
        .withPosition(3,0)
        .withSize(4,4)
        .withProperties(Map.of("Glyph","CAMERA_RETRO","Show Glyph",true,"Show crosshair",false,"Crosshair color","#333333","Show controls",false))
        .withWidget(BuiltInWidgets.kCameraStream);
    }
    //Select the Robot tab by default
    Shuffleboard.selectTab("Robot");
  }

  //This runs at when the robot is disabled. Useful for resetting things back to a starting configuration
  @Override
  public void disabledInit() {
    Logger.write("disabledInit: Resetting drivetrain to high gear");
    m_gearShift.set(DoubleSolenoid.Value.kForward); //reset to high gear
    Logger.write("disabledInit: Retracting intake");
    m_intake.set(!Constants.Intake.kAirStateDeployed); //retract the intake
  }

  //This runs at the beginning of teleop
  @Override
  public void teleopInit() {
  }

  //This runs every loop during teleop
  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    m_robotDrive.arcadeDrive(
      OI.deadband(-m_driver.getRawAxis(Constants.IDs.OI.axis_throttle)) * Constants.DriveTrain.kSpeedMultiplier, 
      OI.deadband(m_driver.getRawAxis(Constants.IDs.OI.axis_turn)) * Constants.DriveTrain.kTurnMutliplier
    );

    //Handle the gear shifting
    if(m_driver.getRawButtonPressed(Constants.IDs.OI.btn_shiftLow)) { //shift to low gear
      Logger.write("teleopPeriodic: Shifting to low gear -- I am STRONG!");
      m_gearShift.set(DoubleSolenoid.Value.kReverse);
    } else if(m_driver.getRawButtonPressed(Constants.IDs.OI.btn_shiftHigh)) { //shift to high gear
      Logger.write("teleopPeriodic: Shifting to high gear -- I am SPEED!");
      m_gearShift.set(DoubleSolenoid.Value.kForward); 
    }

    //Handle the intake controls
    if(m_driver.getRawButtonPressed(Constants.IDs.OI.btn_IntakeDeploy)) { //lower collector
      Logger.write("teleopPeriodic: Deploying Intake");
      m_intake.set(Constants.Intake.kAirStateDeployed);
    } else if(m_driver.getRawButtonPressed(Constants.IDs.OI.btn_IntakeDeploy)) { //raise collector
      Logger.write("teleopPeriodic: Retracting Intake");
      m_intake.set(!Constants.Intake.kAirStateDeployed);
    }
  }

  //This runs at the beginning of autonomous
  @Override
  public void autonomousInit() {
    m_timer.reset(); //reset the timer
    m_timer.start(); //start the timer
    m_autonSelected = m_autonChooser.getSelected();
    Logger.write("autonomousInit: Running auton '"+m_autonSelected+"'");
  }

  //This runs every loop during autonomous
  @Override
  public void autonomousPeriodic() {
    switch (m_autonSelected) { //This defines each auton. They must match the object names exactly of the things added to the chooser in robotInit
      case "autoDriveBackward":
        if (m_timer.get() < 2.0) { //2 seconds
          m_robotDrive.arcadeDrive(-Constants.Auton.kAutonDriveSpeed, 0.0); //drive backward
        } else {
          m_robotDrive.stopMotor(); //stop
        }
        break;
      case "autoDriveForward":
        if (m_timer.get() < 2.0) { //2 seconds
          m_robotDrive.arcadeDrive(Constants.Auton.kAutonDriveSpeed, 0.0); //drive forward
        } else {
          m_robotDrive.stopMotor(); //stop
        }
        break;
      case "autoDoNothing": //Do Nothing auton
      default: //Anything not configured above
        m_robotDrive.stopMotor();
        break;
    }
  }
}
