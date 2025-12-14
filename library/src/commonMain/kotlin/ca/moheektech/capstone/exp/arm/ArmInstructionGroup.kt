package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum

/**
 * Capstone ARM instruction group.
 */
expect enum class ArmInstructionGroup : INumericEnum {

        INVALID, ///< = CS_GRP_INVALID

        // Generic groups
        // all jump instructions (conditional+direct+indirect jumps)
        JUMP, ///< = CS_GRP_JUMP
        CALL, ///< = CS_GRP_CALL
        RET, ///<  = CS_GRP_RET
        INT, ///< = CS_GRP_INT
        PRIVILEGE, ///< = CS_GRP_PRIVILEGE
        BRANCH_RELATIVE, ///< = CS_GRP_BRANCH_RELATIVE

        // Architecture-specific groups
        // generated content <ARMGenCSFeatureEnum.inc> begin
        // clang-format off

        HASV4T,
        HASV5T,
        HASV5TE,
        HASV6,
        HASV6M,
        HASV8MBASELINE,
        HASV8MMAINLINE,
        HASV8_1MMAINLINE,
        HASMVEINT,
        HASMVEFLOAT,
        HASCDE,
        HASFPREGS,
        HASFPREGS16,
        HASNOFPREGS16,
        HASFPREGS64,
        HASFPREGSV8_1M,
        HASV6T2,
        HASV6K,
        HASV7,
        HASV8,
        PREV8,
        HASV8_1A,
        HASV8_2A,
        HASV8_3A,
        HASV8_4A,
        HASV8_5A,
        HASV8_6A,
        HASV8_7A,
        HASVFP2,
        HASVFP3,
        HASVFP4,
        HASDPVFP,
        HASFPARMV8,
        HASNEON,
        HASSHA2,
        HASAES,
        HASCRYPTO,
        HASDOTPROD,
        HASCRC,
        HASRAS,
        HASLOB,
        HASPACBTI,
        HASFP16,
        HASFULLFP16,
        HASFP16FML,
        HASBF16,
        HASMATMULINT8,
        HASDIVIDEINTHUMB,
        HASDIVIDEINARM,
        HASDSP,
        HASDB,
        HASDFB,
        HASV7CLREX,
        HASACQUIRERELEASE,
        HASMP,
        HASVIRTUALIZATION,
        HASTRUSTZONE,
        HAS8MSECEXT,
        ISTHUMB,
        ISTHUMB2,
        ISMCLASS,
        ISNOTMCLASS,
        ISARM,
        USENACLTRAP,
        USENEGATIVEIMMEDIATES,
        HASSB,
        HASCLRBHB,

        // clang-format on
        // generated content <ARMGenCSFeatureEnum.inc> end

        ENDING,
    
}