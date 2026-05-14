import java.lang.module.Configuration;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GainSchedKpBehaviorValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.LimitSwitchConfig.Behavior;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class Confguration {
    public TalonFX motor1;
    public TalonFX motor2;

    private TalonFXConfiguration TalonConfig;
    private SparkMaxConfig SparkConfig;

    public Configuration(){
        motor1 = new TalonFX(0);
        motor2 = new TalonFX(0);

        TalonConfig = new TalonFXConfiguration();
        SparkConfig = new SparkMaxConfig();

        TalonConfig.MotorOutput
            .withInverted(InvertedValue.Clockwise_Positive)
            .withNeutralMode(NeutralModeValue.Brake);
        TalonConfig.Feedback
            .withSensorToMechanismRatio(1)
            .withRotorToSensorRatio(1)
            .withFusedCANcoder(new CANcoder(0))
            .withSyncCANcoder(new CANcoder(0))
            .withRemoteCANcoder(new CANcoder(0));
        TalonConfig.ClosedLoopGeneral
            .withGainSchedKpBehavior(GainSchedKpBehaviorValue.Continuous);
        TalonConfig.SoftwareLimitSwitch
            .withForwardSoftLimitThreshold(0)
            .withReverseSoftLimitThreshold(0)
            .withForwardSoftLimitEnable(true)
            .withForwardSoftLimitEnable(true);
        TalonConfig.CurrentLimits
            .withSupplyCurrentLimit(40)
            .withStatorCurrentLimit(40)
            .withStatorCurrentLimitEnable(true)
            .withSupplyCurrentLimitEnable(true);
        TalonConfig.Slot0
            .withKP(0).withKI(0).withKD(0)
            .withKS(0).withKV(0).withKA(0).withKG(0)
            .withStaticFeedforwardSign(StaticFeedforwardSignValue.UseClosedLoopSign)
            .withGravityType(GravityTypeValue.Arm_Cosine);
        TalonConfig.MotionMagic
            .withMotionMagicCruiseVelocity(0)
            .withMotionMagicAcceleration(0)
            .withMotionMagicJerk(0)
            .withMotionMagicExpo_kV(0)
            .withMotionMagicExpo_kA(0);
        
        SparkConfig
            .idleMode(IdleMode.kBrake)
            .inverted(false)
            .voltageCompensation(12)
            .smartCurrentLimit(40);
        SparkConfig.encoder
            .positionConversionFactor(1)
            .velocityConversionFactor(1);
        SparkConfig.limitSwitch
            .limitSwitchPositionSensor(FeedbackSensor.kPrimaryEncoder)
            .forwardLimitSwitchTriggerBehavior(Behavior.kStopMovingMotor)
            .reverseLimitSwitchTriggerBehavior(Behavior.kStopMovingMotor)
            .forwardLimitSwitchPosition(0)
            .reverseLimitSwitchPosition(0);
        SparkConfig.closedLoop
            .pid(0, 0, 0);
        SparkConfig.closedLoop.maxMotion
            .cruiseVelocity(0)
            .maxAcceleration(0);
        SparkConfig.closedLoop.feedForward
            .svag(0, 0, 0, 0);

        

    }
}
