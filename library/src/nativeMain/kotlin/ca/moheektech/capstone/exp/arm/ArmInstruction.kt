package ca.moheektech.capstone.exp.arm

import ca.moheektech.capstone.exp.INumericEnum
import ca.moheektech.capstone.internal.arm_insn as insn

actual enum class ArmInstruction(override val value: UInt): INumericEnum {
    INVALID(insn.ARM_INS_INVALID.value), ASR(insn.ARM_INS_ASR.value), IT(insn.ARM_INS_IT.value), LDRBT(insn.ARM_INS_LDRBT.value), LDR(
        insn.ARM_INS_LDR.value
    ),
    LDRHT(insn.ARM_INS_LDRHT.value), LDRSBT(insn.ARM_INS_LDRSBT.value), LDRSHT(insn.ARM_INS_LDRSHT.value), LDRT(insn.ARM_INS_LDRT.value), LSL(
        insn.ARM_INS_LSL.value
    ),
    LSR(insn.ARM_INS_LSR.value), ROR(insn.ARM_INS_ROR.value), RRX(insn.ARM_INS_RRX.value), STRBT(insn.ARM_INS_STRBT.value), STRT(
        insn.ARM_INS_STRT.value
    ),
    VLD1(insn.ARM_INS_VLD1.value), VLD2(insn.ARM_INS_VLD2.value), VLD3(insn.ARM_INS_VLD3.value), VLD4(insn.ARM_INS_VLD4.value), VST1(
        insn.ARM_INS_VST1.value
    ),
    VST2(insn.ARM_INS_VST2.value), VST3(insn.ARM_INS_VST3.value), VST4(insn.ARM_INS_VST4.value), LDRB(insn.ARM_INS_LDRB.value), LDRH(
        insn.ARM_INS_LDRH.value
    ),
    LDRSB(insn.ARM_INS_LDRSB.value), LDRSH(insn.ARM_INS_LDRSH.value), MOVS(insn.ARM_INS_MOVS.value), MOV(insn.ARM_INS_MOV.value), STRB(
        insn.ARM_INS_STRB.value
    ),
    STRH(insn.ARM_INS_STRH.value), STR(insn.ARM_INS_STR.value), ADC(insn.ARM_INS_ADC.value), ADD(insn.ARM_INS_ADD.value), ADR(
        insn.ARM_INS_ADR.value
    ),
    AESD(insn.ARM_INS_AESD.value), AESE(insn.ARM_INS_AESE.value), AESIMC(insn.ARM_INS_AESIMC.value), AESMC(insn.ARM_INS_AESMC.value), AND(
        insn.ARM_INS_AND.value
    ),
    VDOT(insn.ARM_INS_VDOT.value), VCVT(insn.ARM_INS_VCVT.value), VCVTB(insn.ARM_INS_VCVTB.value), VCVTT(insn.ARM_INS_VCVTT.value), BFC(
        insn.ARM_INS_BFC.value
    ),
    BFI(insn.ARM_INS_BFI.value), BIC(insn.ARM_INS_BIC.value), BKPT(insn.ARM_INS_BKPT.value), BL(insn.ARM_INS_BL.value), BLX(
        insn.ARM_INS_BLX.value
    ),
    BX(insn.ARM_INS_BX.value), BXJ(insn.ARM_INS_BXJ.value), B(insn.ARM_INS_B.value), CX1(insn.ARM_INS_CX1.value), CX1A(insn.ARM_INS_CX1A.value), CX1D(
        insn.ARM_INS_CX1D.value
    ),
    CX1DA(insn.ARM_INS_CX1DA.value), CX2(insn.ARM_INS_CX2.value), CX2A(insn.ARM_INS_CX2A.value), CX2D(insn.ARM_INS_CX2D.value), CX2DA(
        insn.ARM_INS_CX2DA.value
    ),
    CX3(insn.ARM_INS_CX3.value), CX3A(insn.ARM_INS_CX3A.value), CX3D(insn.ARM_INS_CX3D.value), CX3DA(insn.ARM_INS_CX3DA.value), VCX1A(
        insn.ARM_INS_VCX1A.value
    ),
    VCX1(insn.ARM_INS_VCX1.value), VCX2A(insn.ARM_INS_VCX2A.value), VCX2(insn.ARM_INS_VCX2.value), VCX3A(insn.ARM_INS_VCX3A.value), VCX3(
        insn.ARM_INS_VCX3.value
    ),
    CDP(insn.ARM_INS_CDP.value), CDP2(insn.ARM_INS_CDP2.value), CLREX(insn.ARM_INS_CLREX.value), CLZ(insn.ARM_INS_CLZ.value), CMN(
        insn.ARM_INS_CMN.value
    ),
    CMP(insn.ARM_INS_CMP.value), CPS(insn.ARM_INS_CPS.value), CRC32B(insn.ARM_INS_CRC32B.value), CRC32CB(insn.ARM_INS_CRC32CB.value), CRC32CH(
        insn.ARM_INS_CRC32CH.value
    ),
    CRC32CW(insn.ARM_INS_CRC32CW.value), CRC32H(insn.ARM_INS_CRC32H.value), CRC32W(insn.ARM_INS_CRC32W.value), DBG(
        insn.ARM_INS_DBG.value
    ),
    DMB(insn.ARM_INS_DMB.value), DSB(insn.ARM_INS_DSB.value), EOR(insn.ARM_INS_EOR.value), ERET(insn.ARM_INS_ERET.value), VMOV(
        insn.ARM_INS_VMOV.value
    ),
    FLDMDBX(insn.ARM_INS_FLDMDBX.value), FLDMIAX(insn.ARM_INS_FLDMIAX.value), VMRS(insn.ARM_INS_VMRS.value), FSTMDBX(
        insn.ARM_INS_FSTMDBX.value
    ),
    FSTMIAX(insn.ARM_INS_FSTMIAX.value), HINT(insn.ARM_INS_HINT.value), HLT(insn.ARM_INS_HLT.value), HVC(insn.ARM_INS_HVC.value), ISB(
        insn.ARM_INS_ISB.value
    ),
    LDA(insn.ARM_INS_LDA.value), LDAB(insn.ARM_INS_LDAB.value), LDAEX(insn.ARM_INS_LDAEX.value), LDAEXB(insn.ARM_INS_LDAEXB.value), LDAEXD(
        insn.ARM_INS_LDAEXD.value
    ),
    LDAEXH(insn.ARM_INS_LDAEXH.value), LDAH(insn.ARM_INS_LDAH.value), LDC2L(insn.ARM_INS_LDC2L.value), LDC2(insn.ARM_INS_LDC2.value), LDCL(
        insn.ARM_INS_LDCL.value
    ),
    LDC(insn.ARM_INS_LDC.value), LDMDA(insn.ARM_INS_LDMDA.value), LDMDB(insn.ARM_INS_LDMDB.value), LDM(insn.ARM_INS_LDM.value), LDMIB(
        insn.ARM_INS_LDMIB.value
    ),
    LDRD(insn.ARM_INS_LDRD.value), LDREX(insn.ARM_INS_LDREX.value), LDREXB(insn.ARM_INS_LDREXB.value), LDREXD(insn.ARM_INS_LDREXD.value), LDREXH(
        insn.ARM_INS_LDREXH.value
    ),
    MCR(insn.ARM_INS_MCR.value), MCR2(insn.ARM_INS_MCR2.value), MCRR(insn.ARM_INS_MCRR.value), MCRR2(insn.ARM_INS_MCRR2.value), MLA(
        insn.ARM_INS_MLA.value
    ),
    MLS(insn.ARM_INS_MLS.value), MOVT(insn.ARM_INS_MOVT.value), MOVW(insn.ARM_INS_MOVW.value), MRC(insn.ARM_INS_MRC.value), MRC2(
        insn.ARM_INS_MRC2.value
    ),
    MRRC(insn.ARM_INS_MRRC.value), MRRC2(insn.ARM_INS_MRRC2.value), MRS(insn.ARM_INS_MRS.value), MSR(insn.ARM_INS_MSR.value), MUL(
        insn.ARM_INS_MUL.value
    ),
    ASRL(insn.ARM_INS_ASRL.value), DLSTP(insn.ARM_INS_DLSTP.value), LCTP(insn.ARM_INS_LCTP.value), LETP(insn.ARM_INS_LETP.value), LSLL(
        insn.ARM_INS_LSLL.value
    ),
    LSRL(insn.ARM_INS_LSRL.value), SQRSHR(insn.ARM_INS_SQRSHR.value), SQRSHRL(insn.ARM_INS_SQRSHRL.value), SQSHL(insn.ARM_INS_SQSHL.value), SQSHLL(
        insn.ARM_INS_SQSHLL.value
    ),
    SRSHR(insn.ARM_INS_SRSHR.value), SRSHRL(insn.ARM_INS_SRSHRL.value), UQRSHL(insn.ARM_INS_UQRSHL.value), UQRSHLL(
        insn.ARM_INS_UQRSHLL.value
    ),
    UQSHL(insn.ARM_INS_UQSHL.value), UQSHLL(insn.ARM_INS_UQSHLL.value), URSHR(insn.ARM_INS_URSHR.value), URSHRL(insn.ARM_INS_URSHRL.value), VABAV(
        insn.ARM_INS_VABAV.value
    ),
    VABD(insn.ARM_INS_VABD.value), VABS(insn.ARM_INS_VABS.value), VADC(insn.ARM_INS_VADC.value), VADCI(insn.ARM_INS_VADCI.value), VADDLVA(
        insn.ARM_INS_VADDLVA.value
    ),
    VADDLV(insn.ARM_INS_VADDLV.value), VADDVA(insn.ARM_INS_VADDVA.value), VADDV(insn.ARM_INS_VADDV.value), VADD(insn.ARM_INS_VADD.value), VAND(
        insn.ARM_INS_VAND.value
    ),
    VBIC(insn.ARM_INS_VBIC.value), VBRSR(insn.ARM_INS_VBRSR.value), VCADD(insn.ARM_INS_VCADD.value), VCLS(insn.ARM_INS_VCLS.value), VCLZ(
        insn.ARM_INS_VCLZ.value
    ),
    VCMLA(insn.ARM_INS_VCMLA.value), VCMP(insn.ARM_INS_VCMP.value), VCMUL(insn.ARM_INS_VCMUL.value), VCTP(insn.ARM_INS_VCTP.value), VCVTA(
        insn.ARM_INS_VCVTA.value
    ),
    VCVTM(insn.ARM_INS_VCVTM.value), VCVTN(insn.ARM_INS_VCVTN.value), VCVTP(insn.ARM_INS_VCVTP.value), VDDUP(insn.ARM_INS_VDDUP.value), VDUP(
        insn.ARM_INS_VDUP.value
    ),
    VDWDUP(insn.ARM_INS_VDWDUP.value), VEOR(insn.ARM_INS_VEOR.value), VFMAS(insn.ARM_INS_VFMAS.value), VFMA(insn.ARM_INS_VFMA.value), VFMS(
        insn.ARM_INS_VFMS.value
    ),
    VHADD(insn.ARM_INS_VHADD.value), VHCADD(insn.ARM_INS_VHCADD.value), VHSUB(insn.ARM_INS_VHSUB.value), VIDUP(insn.ARM_INS_VIDUP.value), VIWDUP(
        insn.ARM_INS_VIWDUP.value
    ),
    VLD20(insn.ARM_INS_VLD20.value), VLD21(insn.ARM_INS_VLD21.value), VLD40(insn.ARM_INS_VLD40.value), VLD41(insn.ARM_INS_VLD41.value), VLD42(
        insn.ARM_INS_VLD42.value
    ),
    VLD43(insn.ARM_INS_VLD43.value), VLDRB(insn.ARM_INS_VLDRB.value), VLDRD(insn.ARM_INS_VLDRD.value), VLDRH(insn.ARM_INS_VLDRH.value), VLDRW(
        insn.ARM_INS_VLDRW.value
    ),
    VMAXAV(insn.ARM_INS_VMAXAV.value), VMAXA(insn.ARM_INS_VMAXA.value), VMAXNMAV(insn.ARM_INS_VMAXNMAV.value), VMAXNMA(
        insn.ARM_INS_VMAXNMA.value
    ),
    VMAXNMV(insn.ARM_INS_VMAXNMV.value), VMAXNM(insn.ARM_INS_VMAXNM.value), VMAXV(insn.ARM_INS_VMAXV.value), VMAX(insn.ARM_INS_VMAX.value), VMINAV(
        insn.ARM_INS_VMINAV.value
    ),
    VMINA(insn.ARM_INS_VMINA.value), VMINNMAV(insn.ARM_INS_VMINNMAV.value), VMINNMA(insn.ARM_INS_VMINNMA.value), VMINNMV(
        insn.ARM_INS_VMINNMV.value
    ),
    VMINNM(insn.ARM_INS_VMINNM.value), VMINV(insn.ARM_INS_VMINV.value), VMIN(insn.ARM_INS_VMIN.value), VMLADAVA(insn.ARM_INS_VMLADAVA.value), VMLADAVAX(
        insn.ARM_INS_VMLADAVAX.value
    ),
    VMLADAV(insn.ARM_INS_VMLADAV.value), VMLADAVX(insn.ARM_INS_VMLADAVX.value), VMLALDAVA(insn.ARM_INS_VMLALDAVA.value), VMLALDAVAX(
        insn.ARM_INS_VMLALDAVAX.value
    ),
    VMLALDAV(insn.ARM_INS_VMLALDAV.value), VMLALDAVX(insn.ARM_INS_VMLALDAVX.value), VMLAS(insn.ARM_INS_VMLAS.value), VMLA(
        insn.ARM_INS_VMLA.value
    ),
    VMLSDAVA(insn.ARM_INS_VMLSDAVA.value), VMLSDAVAX(insn.ARM_INS_VMLSDAVAX.value), VMLSDAV(insn.ARM_INS_VMLSDAV.value), VMLSDAVX(
        insn.ARM_INS_VMLSDAVX.value
    ),
    VMLSLDAVA(insn.ARM_INS_VMLSLDAVA.value), VMLSLDAVAX(insn.ARM_INS_VMLSLDAVAX.value), VMLSLDAV(insn.ARM_INS_VMLSLDAV.value), VMLSLDAVX(
        insn.ARM_INS_VMLSLDAVX.value
    ),
    VMOVLB(insn.ARM_INS_VMOVLB.value), VMOVLT(insn.ARM_INS_VMOVLT.value), VMOVNB(insn.ARM_INS_VMOVNB.value), VMOVNT(
        insn.ARM_INS_VMOVNT.value
    ),
    VMULH(insn.ARM_INS_VMULH.value), VMULLB(insn.ARM_INS_VMULLB.value), VMULLT(insn.ARM_INS_VMULLT.value), VMUL(insn.ARM_INS_VMUL.value), VMVN(
        insn.ARM_INS_VMVN.value
    ),
    VNEG(insn.ARM_INS_VNEG.value), VORN(insn.ARM_INS_VORN.value), VORR(insn.ARM_INS_VORR.value), VPNOT(insn.ARM_INS_VPNOT.value), VPSEL(
        insn.ARM_INS_VPSEL.value
    ),
    VPST(insn.ARM_INS_VPST.value), VPT(insn.ARM_INS_VPT.value), VQABS(insn.ARM_INS_VQABS.value), VQADD(insn.ARM_INS_VQADD.value), VQDMLADHX(
        insn.ARM_INS_VQDMLADHX.value
    ),
    VQDMLADH(insn.ARM_INS_VQDMLADH.value), VQDMLAH(insn.ARM_INS_VQDMLAH.value), VQDMLASH(insn.ARM_INS_VQDMLASH.value), VQDMLSDHX(
        insn.ARM_INS_VQDMLSDHX.value
    ),
    VQDMLSDH(insn.ARM_INS_VQDMLSDH.value), VQDMULH(insn.ARM_INS_VQDMULH.value), VQDMULLB(insn.ARM_INS_VQDMULLB.value), VQDMULLT(
        insn.ARM_INS_VQDMULLT.value
    ),
    VQMOVNB(insn.ARM_INS_VQMOVNB.value), VQMOVNT(insn.ARM_INS_VQMOVNT.value), VQMOVUNB(insn.ARM_INS_VQMOVUNB.value), VQMOVUNT(
        insn.ARM_INS_VQMOVUNT.value
    ),
    VQNEG(insn.ARM_INS_VQNEG.value), VQRDMLADHX(insn.ARM_INS_VQRDMLADHX.value), VQRDMLADH(insn.ARM_INS_VQRDMLADH.value), VQRDMLAH(
        insn.ARM_INS_VQRDMLAH.value
    ),
    VQRDMLASH(insn.ARM_INS_VQRDMLASH.value), VQRDMLSDHX(insn.ARM_INS_VQRDMLSDHX.value), VQRDMLSDH(insn.ARM_INS_VQRDMLSDH.value), VQRDMULH(
        insn.ARM_INS_VQRDMULH.value
    ),
    VQRSHL(insn.ARM_INS_VQRSHL.value), VQRSHRNB(insn.ARM_INS_VQRSHRNB.value), VQRSHRNT(insn.ARM_INS_VQRSHRNT.value), VQRSHRUNB(
        insn.ARM_INS_VQRSHRUNB.value
    ),
    VQRSHRUNT(insn.ARM_INS_VQRSHRUNT.value), VQSHLU(insn.ARM_INS_VQSHLU.value), VQSHL(insn.ARM_INS_VQSHL.value), VQSHRNB(
        insn.ARM_INS_VQSHRNB.value
    ),
    VQSHRNT(insn.ARM_INS_VQSHRNT.value), VQSHRUNB(insn.ARM_INS_VQSHRUNB.value), VQSHRUNT(insn.ARM_INS_VQSHRUNT.value), VQSUB(
        insn.ARM_INS_VQSUB.value
    ),
    VREV16(insn.ARM_INS_VREV16.value), VREV32(insn.ARM_INS_VREV32.value), VREV64(insn.ARM_INS_VREV64.value), VRHADD(
        insn.ARM_INS_VRHADD.value
    ),
    VRINTA(insn.ARM_INS_VRINTA.value), VRINTM(insn.ARM_INS_VRINTM.value), VRINTN(insn.ARM_INS_VRINTN.value), VRINTP(
        insn.ARM_INS_VRINTP.value
    ),
    VRINTX(insn.ARM_INS_VRINTX.value), VRINTZ(insn.ARM_INS_VRINTZ.value), VRMLALDAVHA(insn.ARM_INS_VRMLALDAVHA.value), VRMLALDAVHAX(
        insn.ARM_INS_VRMLALDAVHAX.value
    ),
    VRMLALDAVH(insn.ARM_INS_VRMLALDAVH.value), VRMLALDAVHX(insn.ARM_INS_VRMLALDAVHX.value), VRMLSLDAVHA(insn.ARM_INS_VRMLSLDAVHA.value), VRMLSLDAVHAX(
        insn.ARM_INS_VRMLSLDAVHAX.value
    ),
    VRMLSLDAVH(insn.ARM_INS_VRMLSLDAVH.value), VRMLSLDAVHX(insn.ARM_INS_VRMLSLDAVHX.value), VRMULH(insn.ARM_INS_VRMULH.value), VRSHL(
        insn.ARM_INS_VRSHL.value
    ),
    VRSHRNB(insn.ARM_INS_VRSHRNB.value), VRSHRNT(insn.ARM_INS_VRSHRNT.value), VRSHR(insn.ARM_INS_VRSHR.value), VSBC(
        insn.ARM_INS_VSBC.value
    ),
    VSBCI(insn.ARM_INS_VSBCI.value), VSHLC(insn.ARM_INS_VSHLC.value), VSHLLB(insn.ARM_INS_VSHLLB.value), VSHLLT(insn.ARM_INS_VSHLLT.value), VSHL(
        insn.ARM_INS_VSHL.value
    ),
    VSHRNB(insn.ARM_INS_VSHRNB.value), VSHRNT(insn.ARM_INS_VSHRNT.value), VSHR(insn.ARM_INS_VSHR.value), VSLI(insn.ARM_INS_VSLI.value), VSRI(
        insn.ARM_INS_VSRI.value
    ),
    VST20(insn.ARM_INS_VST20.value), VST21(insn.ARM_INS_VST21.value), VST40(insn.ARM_INS_VST40.value), VST41(insn.ARM_INS_VST41.value), VST42(
        insn.ARM_INS_VST42.value
    ),
    VST43(insn.ARM_INS_VST43.value), VSTRB(insn.ARM_INS_VSTRB.value), VSTRD(insn.ARM_INS_VSTRD.value), VSTRH(insn.ARM_INS_VSTRH.value), VSTRW(
        insn.ARM_INS_VSTRW.value
    ),
    VSUB(insn.ARM_INS_VSUB.value), WLSTP(insn.ARM_INS_WLSTP.value), MVN(insn.ARM_INS_MVN.value), ORR(insn.ARM_INS_ORR.value), PKHBT(
        insn.ARM_INS_PKHBT.value
    ),
    PKHTB(insn.ARM_INS_PKHTB.value), PLDW(insn.ARM_INS_PLDW.value), PLD(insn.ARM_INS_PLD.value), PLI(insn.ARM_INS_PLI.value), QADD(
        insn.ARM_INS_QADD.value
    ),
    QADD16(insn.ARM_INS_QADD16.value), QADD8(insn.ARM_INS_QADD8.value), QASX(insn.ARM_INS_QASX.value), QDADD(insn.ARM_INS_QDADD.value), QDSUB(
        insn.ARM_INS_QDSUB.value
    ),
    QSAX(insn.ARM_INS_QSAX.value), QSUB(insn.ARM_INS_QSUB.value), QSUB16(insn.ARM_INS_QSUB16.value), QSUB8(insn.ARM_INS_QSUB8.value), RBIT(
        insn.ARM_INS_RBIT.value
    ),
    REV(insn.ARM_INS_REV.value), REV16(insn.ARM_INS_REV16.value), REVSH(insn.ARM_INS_REVSH.value), RFEDA(insn.ARM_INS_RFEDA.value), RFEDB(
        insn.ARM_INS_RFEDB.value
    ),
    RFEIA(insn.ARM_INS_RFEIA.value), RFEIB(insn.ARM_INS_RFEIB.value), RSB(insn.ARM_INS_RSB.value), RSC(insn.ARM_INS_RSC.value), SADD16(
        insn.ARM_INS_SADD16.value
    ),
    SADD8(insn.ARM_INS_SADD8.value), SASX(insn.ARM_INS_SASX.value), SB(insn.ARM_INS_SB.value), SBC(insn.ARM_INS_SBC.value), SBFX(
        insn.ARM_INS_SBFX.value
    ),
    SDIV(insn.ARM_INS_SDIV.value), SEL(insn.ARM_INS_SEL.value), SETEND(insn.ARM_INS_SETEND.value), SETPAN(insn.ARM_INS_SETPAN.value), SHA1C(
        insn.ARM_INS_SHA1C.value
    ),
    SHA1H(insn.ARM_INS_SHA1H.value), SHA1M(insn.ARM_INS_SHA1M.value), SHA1P(insn.ARM_INS_SHA1P.value), SHA1SU0(insn.ARM_INS_SHA1SU0.value), SHA1SU1(
        insn.ARM_INS_SHA1SU1.value
    ),
    SHA256H(insn.ARM_INS_SHA256H.value), SHA256H2(insn.ARM_INS_SHA256H2.value), SHA256SU0(insn.ARM_INS_SHA256SU0.value), SHA256SU1(
        insn.ARM_INS_SHA256SU1.value
    ),
    SHADD16(insn.ARM_INS_SHADD16.value), SHADD8(insn.ARM_INS_SHADD8.value), SHASX(insn.ARM_INS_SHASX.value), SHSAX(
        insn.ARM_INS_SHSAX.value
    ),
    SHSUB16(insn.ARM_INS_SHSUB16.value), SHSUB8(insn.ARM_INS_SHSUB8.value), SMC(insn.ARM_INS_SMC.value), SMLABB(insn.ARM_INS_SMLABB.value), SMLABT(
        insn.ARM_INS_SMLABT.value
    ),
    SMLAD(insn.ARM_INS_SMLAD.value), SMLADX(insn.ARM_INS_SMLADX.value), SMLAL(insn.ARM_INS_SMLAL.value), SMLALBB(insn.ARM_INS_SMLALBB.value), SMLALBT(
        insn.ARM_INS_SMLALBT.value
    ),
    SMLALD(insn.ARM_INS_SMLALD.value), SMLALDX(insn.ARM_INS_SMLALDX.value), SMLALTB(insn.ARM_INS_SMLALTB.value), SMLALTT(
        insn.ARM_INS_SMLALTT.value
    ),
    SMLATB(insn.ARM_INS_SMLATB.value), SMLATT(insn.ARM_INS_SMLATT.value), SMLAWB(insn.ARM_INS_SMLAWB.value), SMLAWT(
        insn.ARM_INS_SMLAWT.value
    ),
    SMLSD(insn.ARM_INS_SMLSD.value), SMLSDX(insn.ARM_INS_SMLSDX.value), SMLSLD(insn.ARM_INS_SMLSLD.value), SMLSLDX(
        insn.ARM_INS_SMLSLDX.value
    ),
    SMMLA(insn.ARM_INS_SMMLA.value), SMMLAR(insn.ARM_INS_SMMLAR.value), SMMLS(insn.ARM_INS_SMMLS.value), SMMLSR(insn.ARM_INS_SMMLSR.value), SMMUL(
        insn.ARM_INS_SMMUL.value
    ),
    SMMULR(insn.ARM_INS_SMMULR.value), SMUAD(insn.ARM_INS_SMUAD.value), SMUADX(insn.ARM_INS_SMUADX.value), SMULBB(insn.ARM_INS_SMULBB.value), SMULBT(
        insn.ARM_INS_SMULBT.value
    ),
    SMULL(insn.ARM_INS_SMULL.value), SMULTB(insn.ARM_INS_SMULTB.value), SMULTT(insn.ARM_INS_SMULTT.value), SMULWB(insn.ARM_INS_SMULWB.value), SMULWT(
        insn.ARM_INS_SMULWT.value
    ),
    SMUSD(insn.ARM_INS_SMUSD.value), SMUSDX(insn.ARM_INS_SMUSDX.value), SRSDA(insn.ARM_INS_SRSDA.value), SRSDB(insn.ARM_INS_SRSDB.value), SRSIA(
        insn.ARM_INS_SRSIA.value
    ),
    SRSIB(insn.ARM_INS_SRSIB.value), SSAT(insn.ARM_INS_SSAT.value), SSAT16(insn.ARM_INS_SSAT16.value), SSAX(insn.ARM_INS_SSAX.value), SSUB16(
        insn.ARM_INS_SSUB16.value
    ),
    SSUB8(insn.ARM_INS_SSUB8.value), STC2L(insn.ARM_INS_STC2L.value), STC2(insn.ARM_INS_STC2.value), STCL(insn.ARM_INS_STCL.value), STC(
        insn.ARM_INS_STC.value
    ),
    STL(insn.ARM_INS_STL.value), STLB(insn.ARM_INS_STLB.value), STLEX(insn.ARM_INS_STLEX.value), STLEXB(insn.ARM_INS_STLEXB.value), STLEXD(
        insn.ARM_INS_STLEXD.value
    ),
    STLEXH(insn.ARM_INS_STLEXH.value), STLH(insn.ARM_INS_STLH.value), STMDA(insn.ARM_INS_STMDA.value), STMDB(insn.ARM_INS_STMDB.value), STM(
        insn.ARM_INS_STM.value
    ),
    STMIB(insn.ARM_INS_STMIB.value), STRD(insn.ARM_INS_STRD.value), STREX(insn.ARM_INS_STREX.value), STREXB(insn.ARM_INS_STREXB.value), STREXD(
        insn.ARM_INS_STREXD.value
    ),
    STREXH(insn.ARM_INS_STREXH.value), STRHT(insn.ARM_INS_STRHT.value), SUB(insn.ARM_INS_SUB.value), SVC(insn.ARM_INS_SVC.value), SWP(
        insn.ARM_INS_SWP.value
    ),
    SWPB(insn.ARM_INS_SWPB.value), SXTAB(insn.ARM_INS_SXTAB.value), SXTAB16(insn.ARM_INS_SXTAB16.value), SXTAH(insn.ARM_INS_SXTAH.value), SXTB(
        insn.ARM_INS_SXTB.value
    ),
    SXTB16(insn.ARM_INS_SXTB16.value), SXTH(insn.ARM_INS_SXTH.value), TEQ(insn.ARM_INS_TEQ.value), TRAP(insn.ARM_INS_TRAP.value), TSB(
        insn.ARM_INS_TSB.value
    ),
    TST(insn.ARM_INS_TST.value), UADD16(insn.ARM_INS_UADD16.value), UADD8(insn.ARM_INS_UADD8.value), UASX(insn.ARM_INS_UASX.value), UBFX(
        insn.ARM_INS_UBFX.value
    ),
    UDF(insn.ARM_INS_UDF.value), UDIV(insn.ARM_INS_UDIV.value), UHADD16(insn.ARM_INS_UHADD16.value), UHADD8(insn.ARM_INS_UHADD8.value), UHASX(
        insn.ARM_INS_UHASX.value
    ),
    UHSAX(insn.ARM_INS_UHSAX.value), UHSUB16(insn.ARM_INS_UHSUB16.value), UHSUB8(insn.ARM_INS_UHSUB8.value), UMAAL(
        insn.ARM_INS_UMAAL.value
    ),
    UMLAL(insn.ARM_INS_UMLAL.value), UMULL(insn.ARM_INS_UMULL.value), UQADD16(insn.ARM_INS_UQADD16.value), UQADD8(insn.ARM_INS_UQADD8.value), UQASX(
        insn.ARM_INS_UQASX.value
    ),
    UQSAX(insn.ARM_INS_UQSAX.value), UQSUB16(insn.ARM_INS_UQSUB16.value), UQSUB8(insn.ARM_INS_UQSUB8.value), USAD8(
        insn.ARM_INS_USAD8.value
    ),
    USADA8(insn.ARM_INS_USADA8.value), USAT(insn.ARM_INS_USAT.value), USAT16(insn.ARM_INS_USAT16.value), USAX(insn.ARM_INS_USAX.value), USUB16(
        insn.ARM_INS_USUB16.value
    ),
    USUB8(insn.ARM_INS_USUB8.value), UXTAB(insn.ARM_INS_UXTAB.value), UXTAB16(insn.ARM_INS_UXTAB16.value), UXTAH(insn.ARM_INS_UXTAH.value), UXTB(
        insn.ARM_INS_UXTB.value
    ),
    UXTB16(insn.ARM_INS_UXTB16.value), UXTH(insn.ARM_INS_UXTH.value), VABAL(insn.ARM_INS_VABAL.value), VABA(insn.ARM_INS_VABA.value), VABDL(
        insn.ARM_INS_VABDL.value
    ),
    VACGE(insn.ARM_INS_VACGE.value), VACGT(insn.ARM_INS_VACGT.value), VADDHN(insn.ARM_INS_VADDHN.value), VADDL(insn.ARM_INS_VADDL.value), VADDW(
        insn.ARM_INS_VADDW.value
    ),
    VFMAB(insn.ARM_INS_VFMAB.value), VFMAT(insn.ARM_INS_VFMAT.value), VBIF(insn.ARM_INS_VBIF.value), VBIT(insn.ARM_INS_VBIT.value), VBSL(
        insn.ARM_INS_VBSL.value
    ),
    VCEQ(insn.ARM_INS_VCEQ.value), VCGE(insn.ARM_INS_VCGE.value), VCGT(insn.ARM_INS_VCGT.value), VCLE(insn.ARM_INS_VCLE.value), VCLT(
        insn.ARM_INS_VCLT.value
    ),
    VCMPE(insn.ARM_INS_VCMPE.value), VCNT(insn.ARM_INS_VCNT.value), VDIV(insn.ARM_INS_VDIV.value), VEXT(insn.ARM_INS_VEXT.value), VFMAL(
        insn.ARM_INS_VFMAL.value
    ),
    VFMSL(insn.ARM_INS_VFMSL.value), VFNMA(insn.ARM_INS_VFNMA.value), VFNMS(insn.ARM_INS_VFNMS.value), VINS(insn.ARM_INS_VINS.value), VJCVT(
        insn.ARM_INS_VJCVT.value
    ),
    VLDMDB(insn.ARM_INS_VLDMDB.value), VLDMIA(insn.ARM_INS_VLDMIA.value), VLDR(insn.ARM_INS_VLDR.value), VLLDM(insn.ARM_INS_VLLDM.value), VLSTM(
        insn.ARM_INS_VLSTM.value
    ),
    VMLAL(insn.ARM_INS_VMLAL.value), VMLS(insn.ARM_INS_VMLS.value), VMLSL(insn.ARM_INS_VMLSL.value), VMMLA(insn.ARM_INS_VMMLA.value), VMOVX(
        insn.ARM_INS_VMOVX.value
    ),
    VMOVL(insn.ARM_INS_VMOVL.value), VMOVN(insn.ARM_INS_VMOVN.value), VMSR(insn.ARM_INS_VMSR.value), VMULL(insn.ARM_INS_VMULL.value), VNMLA(
        insn.ARM_INS_VNMLA.value
    ),
    VNMLS(insn.ARM_INS_VNMLS.value), VNMUL(insn.ARM_INS_VNMUL.value), VPADAL(insn.ARM_INS_VPADAL.value), VPADDL(insn.ARM_INS_VPADDL.value), VPADD(
        insn.ARM_INS_VPADD.value
    ),
    VPMAX(insn.ARM_INS_VPMAX.value), VPMIN(insn.ARM_INS_VPMIN.value), VQDMLAL(insn.ARM_INS_VQDMLAL.value), VQDMLSL(
        insn.ARM_INS_VQDMLSL.value
    ),
    VQDMULL(insn.ARM_INS_VQDMULL.value), VQMOVUN(insn.ARM_INS_VQMOVUN.value), VQMOVN(insn.ARM_INS_VQMOVN.value), VQRDMLSH(
        insn.ARM_INS_VQRDMLSH.value
    ),
    VQRSHRN(insn.ARM_INS_VQRSHRN.value), VQRSHRUN(insn.ARM_INS_VQRSHRUN.value), VQSHRN(insn.ARM_INS_VQSHRN.value), VQSHRUN(
        insn.ARM_INS_VQSHRUN.value
    ),
    VRADDHN(insn.ARM_INS_VRADDHN.value), VRECPE(insn.ARM_INS_VRECPE.value), VRECPS(insn.ARM_INS_VRECPS.value), VRINTR(
        insn.ARM_INS_VRINTR.value
    ),
    VRSHRN(insn.ARM_INS_VRSHRN.value), VRSQRTE(insn.ARM_INS_VRSQRTE.value), VRSQRTS(insn.ARM_INS_VRSQRTS.value), VRSRA(
        insn.ARM_INS_VRSRA.value
    ),
    VRSUBHN(insn.ARM_INS_VRSUBHN.value), VSCCLRM(insn.ARM_INS_VSCCLRM.value), VSDOT(insn.ARM_INS_VSDOT.value), VSELEQ(
        insn.ARM_INS_VSELEQ.value
    ),
    VSELGE(insn.ARM_INS_VSELGE.value), VSELGT(insn.ARM_INS_VSELGT.value), VSELVS(insn.ARM_INS_VSELVS.value), VSHLL(
        insn.ARM_INS_VSHLL.value
    ),
    VSHRN(insn.ARM_INS_VSHRN.value), VSMMLA(insn.ARM_INS_VSMMLA.value), VSQRT(insn.ARM_INS_VSQRT.value), VSRA(insn.ARM_INS_VSRA.value), VSTMDB(
        insn.ARM_INS_VSTMDB.value
    ),
    VSTMIA(insn.ARM_INS_VSTMIA.value), VSTR(insn.ARM_INS_VSTR.value), VSUBHN(insn.ARM_INS_VSUBHN.value), VSUBL(insn.ARM_INS_VSUBL.value), VSUBW(
        insn.ARM_INS_VSUBW.value
    ),
    VSUDOT(insn.ARM_INS_VSUDOT.value), VSWP(insn.ARM_INS_VSWP.value), VTBL(insn.ARM_INS_VTBL.value), VTBX(insn.ARM_INS_VTBX.value), VCVTR(
        insn.ARM_INS_VCVTR.value
    ),
    VTRN(insn.ARM_INS_VTRN.value), VTST(insn.ARM_INS_VTST.value), VUDOT(insn.ARM_INS_VUDOT.value), VUMMLA(insn.ARM_INS_VUMMLA.value), VUSDOT(
        insn.ARM_INS_VUSDOT.value
    ),
    VUSMMLA(insn.ARM_INS_VUSMMLA.value), VUZP(insn.ARM_INS_VUZP.value), VZIP(insn.ARM_INS_VZIP.value), ADDW(insn.ARM_INS_ADDW.value), AUT(
        insn.ARM_INS_AUT.value
    ),
    AUTG(insn.ARM_INS_AUTG.value), BFL(insn.ARM_INS_BFL.value), BFLX(insn.ARM_INS_BFLX.value), BF(insn.ARM_INS_BF.value), BFCSEL(
        insn.ARM_INS_BFCSEL.value
    ),
    BFX(insn.ARM_INS_BFX.value), BTI(insn.ARM_INS_BTI.value), BXAUT(insn.ARM_INS_BXAUT.value), CLRM(insn.ARM_INS_CLRM.value), CSEL(
        insn.ARM_INS_CSEL.value
    ),
    CSINC(insn.ARM_INS_CSINC.value), CSINV(insn.ARM_INS_CSINV.value), CSNEG(insn.ARM_INS_CSNEG.value), DCPS1(insn.ARM_INS_DCPS1.value), DCPS2(
        insn.ARM_INS_DCPS2.value
    ),
    DCPS3(insn.ARM_INS_DCPS3.value), DLS(insn.ARM_INS_DLS.value), LE(insn.ARM_INS_LE.value), ORN(insn.ARM_INS_ORN.value), PAC(
        insn.ARM_INS_PAC.value
    ),
    PACBTI(insn.ARM_INS_PACBTI.value), PACG(insn.ARM_INS_PACG.value), SG(insn.ARM_INS_SG.value), SUBS(insn.ARM_INS_SUBS.value), SUBW(
        insn.ARM_INS_SUBW.value
    ),
    TBB(insn.ARM_INS_TBB.value), TBH(insn.ARM_INS_TBH.value), TT(insn.ARM_INS_TT.value), TTA(insn.ARM_INS_TTA.value), TTAT(
        insn.ARM_INS_TTAT.value
    ),
    TTT(insn.ARM_INS_TTT.value), WLS(insn.ARM_INS_WLS.value), BLXNS(insn.ARM_INS_BLXNS.value), BXNS(insn.ARM_INS_BXNS.value), CBNZ(
        insn.ARM_INS_CBNZ.value
    ),
    CBZ(insn.ARM_INS_CBZ.value), POP(insn.ARM_INS_POP.value), PUSH(insn.ARM_INS_PUSH.value), ARM_INS___BRKDIV0(insn.ARM_INS___BRKDIV0.value),

    // clang-format on
    // generated content <ARMGenCSInsnEnum.inc> end

    ENDING(insn.ARM_INS_ENDING.value), // <-- mark the end of the list of instructions


    ALIAS_BEGIN(insn.ARM_INS_ALIAS_BEGIN.value),
    ALIAS_VMOV(insn.ARM_INS_ALIAS_VMOV.value),
    ALIAS_NOP(insn.ARM_INS_ALIAS_NOP.value),
    ALIAS_VMLAVA(insn.ARM_INS_ALIAS_VMLAVA.value),
    ALIAS_VRMLALVH(insn.ARM_INS_ALIAS_VRMLALVH.value),
    ALIAS_VRMLALVHA(insn.ARM_INS_ALIAS_VRMLALVHA.value),
    ALIAS_VMLALV(insn.ARM_INS_ALIAS_VMLALV.value),
    ALIAS_VMLALVA(insn.ARM_INS_ALIAS_VMLALVA.value),
    ALIAS_VBIC(insn.ARM_INS_ALIAS_VBIC.value),
    ALIAS_VEOR(insn.ARM_INS_ALIAS_VEOR.value),
    ALIAS_VORN(insn.ARM_INS_ALIAS_VORN.value),
    ALIAS_VORR(insn.ARM_INS_ALIAS_VORR.value),
    ALIAS_VAND(insn.ARM_INS_ALIAS_VAND.value),
    ALIAS_VPSEL(insn.ARM_INS_ALIAS_VPSEL.value),
    ALIAS_ERET(insn.ARM_INS_ALIAS_ERET.value),

    // Hardcoded in LLVM printer
    ALIAS_ASR(insn.ARM_INS_ALIAS_ASR.value), ALIAS_LSL(insn.ARM_INS_ALIAS_LSL.value), ALIAS_LSR(insn.ARM_INS_ALIAS_LSR.value), ALIAS_ROR(
        insn.ARM_INS_ALIAS_ROR.value
    ),
    ALIAS_RRX(insn.ARM_INS_ALIAS_RRX.value), ALIAS_UXTW(insn.ARM_INS_ALIAS_UXTW.value), ALIAS_LDM(insn.ARM_INS_ALIAS_LDM.value), ALIAS_POP(
        insn.ARM_INS_ALIAS_POP.value
    ),
    ALIAS_PUSH(insn.ARM_INS_ALIAS_PUSH.value), ALIAS_POPW(insn.ARM_INS_ALIAS_POPW.value), ALIAS_PUSHW(insn.ARM_INS_ALIAS_PUSHW.value), ALIAS_VPOP(
        insn.ARM_INS_ALIAS_VPOP.value
    ),
    ALIAS_VPUSH(insn.ARM_INS_ALIAS_VPUSH.value), ALIAS_END(insn.ARM_INS_ALIAS_END.value),
}