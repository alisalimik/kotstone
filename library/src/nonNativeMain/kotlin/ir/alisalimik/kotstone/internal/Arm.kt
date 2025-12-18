@file:OptIn(ExperimentalJsStatic::class)

package ir.alisalimik.kotstone.internal

import kotlin.js.ExperimentalJsStatic
import kotlin.js.JsStatic

@JsStatic internal const val ARM_BANKEDREG_ELR_HYP = 30

@JsStatic internal const val ARM_BANKEDREG_LR_ABT = 20

@JsStatic internal const val ARM_BANKEDREG_LR_FIQ = 14

@JsStatic internal const val ARM_BANKEDREG_LR_IRQ = 16

@JsStatic internal const val ARM_BANKEDREG_LR_MON = 28

@JsStatic internal const val ARM_BANKEDREG_LR_SVC = 18

@JsStatic internal const val ARM_BANKEDREG_LR_UND = 22

@JsStatic internal const val ARM_BANKEDREG_LR_USR = 6

@JsStatic internal const val ARM_BANKEDREG_R10_FIQ = 10

@JsStatic internal const val ARM_BANKEDREG_R10_USR = 2

@JsStatic internal const val ARM_BANKEDREG_R11_FIQ = 11

@JsStatic internal const val ARM_BANKEDREG_R11_USR = 3

@JsStatic internal const val ARM_BANKEDREG_R12_FIQ = 12

@JsStatic internal const val ARM_BANKEDREG_R12_USR = 4

@JsStatic internal const val ARM_BANKEDREG_R8_FIQ = 8

@JsStatic internal const val ARM_BANKEDREG_R8_USR = 0

@JsStatic internal const val ARM_BANKEDREG_R9_FIQ = 9

@JsStatic internal const val ARM_BANKEDREG_R9_USR = 1

@JsStatic internal const val ARM_BANKEDREG_SPSR_ABT = 52

@JsStatic internal const val ARM_BANKEDREG_SPSR_FIQ = 46

@JsStatic internal const val ARM_BANKEDREG_SPSR_HYP = 62

@JsStatic internal const val ARM_BANKEDREG_SPSR_IRQ = 48

@JsStatic internal const val ARM_BANKEDREG_SPSR_MON = 60

@JsStatic internal const val ARM_BANKEDREG_SPSR_SVC = 50

@JsStatic internal const val ARM_BANKEDREG_SPSR_UND = 54

@JsStatic internal const val ARM_BANKEDREG_SP_ABT = 21

@JsStatic internal const val ARM_BANKEDREG_SP_FIQ = 13

@JsStatic internal const val ARM_BANKEDREG_SP_HYP = 31

@JsStatic internal const val ARM_BANKEDREG_SP_IRQ = 17

@JsStatic internal const val ARM_BANKEDREG_SP_MON = 29

@JsStatic internal const val ARM_BANKEDREG_SP_SVC = 19

@JsStatic internal const val ARM_BANKEDREG_SP_UND = 23

@JsStatic internal const val ARM_BANKEDREG_SP_USR = 5

@JsStatic internal const val ARMCC_EQ = 0

@JsStatic internal const val ARMCC_NE = 1

@JsStatic internal const val ARMCC_HS = 2

@JsStatic internal const val ARMCC_LO = 3

@JsStatic internal const val ARMCC_MI = 4

@JsStatic internal const val ARMCC_PL = 5

@JsStatic internal const val ARMCC_VS = 6

@JsStatic internal const val ARMCC_VC = 7

@JsStatic internal const val ARMCC_HI = 8

@JsStatic internal const val ARMCC_LS = 9

@JsStatic internal const val ARMCC_GE = 10

@JsStatic internal const val ARMCC_LT = 11

@JsStatic internal const val ARMCC_GT = 12

@JsStatic internal const val ARMCC_LE = 13

@JsStatic internal const val ARMCC_AL = 14

@JsStatic internal const val ARMCC_UNDEF = 15

@JsStatic internal const val ARMCC_Invalid = 16

@JsStatic internal const val ARM_CPSFLAG_INVALID = 0

@JsStatic internal const val ARM_CPSFLAG_F = 1

@JsStatic internal const val ARM_CPSFLAG_I = 2

@JsStatic internal const val ARM_CPSFLAG_A = 4

@JsStatic internal const val ARM_CPSFLAG_NONE = 16

@JsStatic internal const val ARM_CPSMODE_INVALID = 0

@JsStatic internal const val ARM_CPSMODE_IE = 2

@JsStatic internal const val ARM_CPSMODE_ID = 3

@JsStatic internal const val ARM_INS_INVALID = 0

@JsStatic internal const val ARM_INS_ASR = 1

@JsStatic internal const val ARM_INS_IT = 2

@JsStatic internal const val ARM_INS_LDRBT = 3

@JsStatic internal const val ARM_INS_LDR = 4

@JsStatic internal const val ARM_INS_LDRHT = 5

@JsStatic internal const val ARM_INS_LDRSBT = 6

@JsStatic internal const val ARM_INS_LDRSHT = 7

@JsStatic internal const val ARM_INS_LDRT = 8

@JsStatic internal const val ARM_INS_LSL = 9

@JsStatic internal const val ARM_INS_LSR = 10

@JsStatic internal const val ARM_INS_ROR = 11

@JsStatic internal const val ARM_INS_RRX = 12

@JsStatic internal const val ARM_INS_STRBT = 13

@JsStatic internal const val ARM_INS_STRT = 14

@JsStatic internal const val ARM_INS_VLD1 = 15

@JsStatic internal const val ARM_INS_VLD2 = 16

@JsStatic internal const val ARM_INS_VLD3 = 17

@JsStatic internal const val ARM_INS_VLD4 = 18

@JsStatic internal const val ARM_INS_VST1 = 19

@JsStatic internal const val ARM_INS_VST2 = 20

@JsStatic internal const val ARM_INS_VST3 = 21

@JsStatic internal const val ARM_INS_VST4 = 22

@JsStatic internal const val ARM_INS_LDRB = 23

@JsStatic internal const val ARM_INS_LDRH = 24

@JsStatic internal const val ARM_INS_LDRSB = 25

@JsStatic internal const val ARM_INS_LDRSH = 26

@JsStatic internal const val ARM_INS_MOVS = 27

@JsStatic internal const val ARM_INS_MOV = 28

@JsStatic internal const val ARM_INS_STRB = 29

@JsStatic internal const val ARM_INS_STRH = 30

@JsStatic internal const val ARM_INS_STR = 31

@JsStatic internal const val ARM_INS_ADC = 32

@JsStatic internal const val ARM_INS_ADD = 33

@JsStatic internal const val ARM_INS_ADR = 34

@JsStatic internal const val ARM_INS_AESD = 35

@JsStatic internal const val ARM_INS_AESE = 36

@JsStatic internal const val ARM_INS_AESIMC = 37

@JsStatic internal const val ARM_INS_AESMC = 38

@JsStatic internal const val ARM_INS_AND = 39

@JsStatic internal const val ARM_INS_VDOT = 40

@JsStatic internal const val ARM_INS_VCVT = 41

@JsStatic internal const val ARM_INS_VCVTB = 42

@JsStatic internal const val ARM_INS_VCVTT = 43

@JsStatic internal const val ARM_INS_BFC = 44

@JsStatic internal const val ARM_INS_BFI = 45

@JsStatic internal const val ARM_INS_BIC = 46

@JsStatic internal const val ARM_INS_BKPT = 47

@JsStatic internal const val ARM_INS_BL = 48

@JsStatic internal const val ARM_INS_BLX = 49

@JsStatic internal const val ARM_INS_BX = 50

@JsStatic internal const val ARM_INS_BXJ = 51

@JsStatic internal const val ARM_INS_B = 52

@JsStatic internal const val ARM_INS_CX1 = 53

@JsStatic internal const val ARM_INS_CX1A = 54

@JsStatic internal const val ARM_INS_CX1D = 55

@JsStatic internal const val ARM_INS_CX1DA = 56

@JsStatic internal const val ARM_INS_CX2 = 57

@JsStatic internal const val ARM_INS_CX2A = 58

@JsStatic internal const val ARM_INS_CX2D = 59

@JsStatic internal const val ARM_INS_CX2DA = 60

@JsStatic internal const val ARM_INS_CX3 = 61

@JsStatic internal const val ARM_INS_CX3A = 62

@JsStatic internal const val ARM_INS_CX3D = 63

@JsStatic internal const val ARM_INS_CX3DA = 64

@JsStatic internal const val ARM_INS_VCX1A = 65

@JsStatic internal const val ARM_INS_VCX1 = 66

@JsStatic internal const val ARM_INS_VCX2A = 67

@JsStatic internal const val ARM_INS_VCX2 = 68

@JsStatic internal const val ARM_INS_VCX3A = 69

@JsStatic internal const val ARM_INS_VCX3 = 70

@JsStatic internal const val ARM_INS_CDP = 71

@JsStatic internal const val ARM_INS_CDP2 = 72

@JsStatic internal const val ARM_INS_CLREX = 73

@JsStatic internal const val ARM_INS_CLZ = 74

@JsStatic internal const val ARM_INS_CMN = 75

@JsStatic internal const val ARM_INS_CMP = 76

@JsStatic internal const val ARM_INS_CPS = 77

@JsStatic internal const val ARM_INS_CRC32B = 78

@JsStatic internal const val ARM_INS_CRC32CB = 79

@JsStatic internal const val ARM_INS_CRC32CH = 80

@JsStatic internal const val ARM_INS_CRC32CW = 81

@JsStatic internal const val ARM_INS_CRC32H = 82

@JsStatic internal const val ARM_INS_CRC32W = 83

@JsStatic internal const val ARM_INS_DBG = 84

@JsStatic internal const val ARM_INS_DMB = 85

@JsStatic internal const val ARM_INS_DSB = 86

@JsStatic internal const val ARM_INS_EOR = 87

@JsStatic internal const val ARM_INS_ERET = 88

@JsStatic internal const val ARM_INS_VMOV = 89

@JsStatic internal const val ARM_INS_FLDMDBX = 90

@JsStatic internal const val ARM_INS_FLDMIAX = 91

@JsStatic internal const val ARM_INS_VMRS = 92

@JsStatic internal const val ARM_INS_FSTMDBX = 93

@JsStatic internal const val ARM_INS_FSTMIAX = 94

@JsStatic internal const val ARM_INS_HINT = 95

@JsStatic internal const val ARM_INS_HLT = 96

@JsStatic internal const val ARM_INS_HVC = 97

@JsStatic internal const val ARM_INS_ISB = 98

@JsStatic internal const val ARM_INS_LDA = 99

@JsStatic internal const val ARM_INS_LDAB = 100

@JsStatic internal const val ARM_INS_LDAEX = 101

@JsStatic internal const val ARM_INS_LDAEXB = 102

@JsStatic internal const val ARM_INS_LDAEXD = 103

@JsStatic internal const val ARM_INS_LDAEXH = 104

@JsStatic internal const val ARM_INS_LDAH = 105

@JsStatic internal const val ARM_INS_LDC2L = 106

@JsStatic internal const val ARM_INS_LDC2 = 107

@JsStatic internal const val ARM_INS_LDCL = 108

@JsStatic internal const val ARM_INS_LDC = 109

@JsStatic internal const val ARM_INS_LDMDA = 110

@JsStatic internal const val ARM_INS_LDMDB = 111

@JsStatic internal const val ARM_INS_LDM = 112

@JsStatic internal const val ARM_INS_LDMIB = 113

@JsStatic internal const val ARM_INS_LDRD = 114

@JsStatic internal const val ARM_INS_LDREX = 115

@JsStatic internal const val ARM_INS_LDREXB = 116

@JsStatic internal const val ARM_INS_LDREXD = 117

@JsStatic internal const val ARM_INS_LDREXH = 118

@JsStatic internal const val ARM_INS_MCR = 119

@JsStatic internal const val ARM_INS_MCR2 = 120

@JsStatic internal const val ARM_INS_MCRR = 121

@JsStatic internal const val ARM_INS_MCRR2 = 122

@JsStatic internal const val ARM_INS_MLA = 123

@JsStatic internal const val ARM_INS_MLS = 124

@JsStatic internal const val ARM_INS_MOVT = 125

@JsStatic internal const val ARM_INS_MOVW = 126

@JsStatic internal const val ARM_INS_MRC = 127

@JsStatic internal const val ARM_INS_MRC2 = 128

@JsStatic internal const val ARM_INS_MRRC = 129

@JsStatic internal const val ARM_INS_MRRC2 = 130

@JsStatic internal const val ARM_INS_MRS = 131

@JsStatic internal const val ARM_INS_MSR = 132

@JsStatic internal const val ARM_INS_MUL = 133

@JsStatic internal const val ARM_INS_ASRL = 134

@JsStatic internal const val ARM_INS_DLSTP = 135

@JsStatic internal const val ARM_INS_LCTP = 136

@JsStatic internal const val ARM_INS_LETP = 137

@JsStatic internal const val ARM_INS_LSLL = 138

@JsStatic internal const val ARM_INS_LSRL = 139

@JsStatic internal const val ARM_INS_SQRSHR = 140

@JsStatic internal const val ARM_INS_SQRSHRL = 141

@JsStatic internal const val ARM_INS_SQSHL = 142

@JsStatic internal const val ARM_INS_SQSHLL = 143

@JsStatic internal const val ARM_INS_SRSHR = 144

@JsStatic internal const val ARM_INS_SRSHRL = 145

@JsStatic internal const val ARM_INS_UQRSHL = 146

@JsStatic internal const val ARM_INS_UQRSHLL = 147

@JsStatic internal const val ARM_INS_UQSHL = 148

@JsStatic internal const val ARM_INS_UQSHLL = 149

@JsStatic internal const val ARM_INS_URSHR = 150

@JsStatic internal const val ARM_INS_URSHRL = 151

@JsStatic internal const val ARM_INS_VABAV = 152

@JsStatic internal const val ARM_INS_VABD = 153

@JsStatic internal const val ARM_INS_VABS = 154

@JsStatic internal const val ARM_INS_VADC = 155

@JsStatic internal const val ARM_INS_VADCI = 156

@JsStatic internal const val ARM_INS_VADDLVA = 157

@JsStatic internal const val ARM_INS_VADDLV = 158

@JsStatic internal const val ARM_INS_VADDVA = 159

@JsStatic internal const val ARM_INS_VADDV = 160

@JsStatic internal const val ARM_INS_VADD = 161

@JsStatic internal const val ARM_INS_VAND = 162

@JsStatic internal const val ARM_INS_VBIC = 163

@JsStatic internal const val ARM_INS_VBRSR = 164

@JsStatic internal const val ARM_INS_VCADD = 165

@JsStatic internal const val ARM_INS_VCLS = 166

@JsStatic internal const val ARM_INS_VCLZ = 167

@JsStatic internal const val ARM_INS_VCMLA = 168

@JsStatic internal const val ARM_INS_VCMP = 169

@JsStatic internal const val ARM_INS_VCMUL = 170

@JsStatic internal const val ARM_INS_VCTP = 171

@JsStatic internal const val ARM_INS_VCVTA = 172

@JsStatic internal const val ARM_INS_VCVTM = 173

@JsStatic internal const val ARM_INS_VCVTN = 174

@JsStatic internal const val ARM_INS_VCVTP = 175

@JsStatic internal const val ARM_INS_VDDUP = 176

@JsStatic internal const val ARM_INS_VDUP = 177

@JsStatic internal const val ARM_INS_VDWDUP = 178

@JsStatic internal const val ARM_INS_VEOR = 179

@JsStatic internal const val ARM_INS_VFMAS = 180

@JsStatic internal const val ARM_INS_VFMA = 181

@JsStatic internal const val ARM_INS_VFMS = 182

@JsStatic internal const val ARM_INS_VHADD = 183

@JsStatic internal const val ARM_INS_VHCADD = 184

@JsStatic internal const val ARM_INS_VHSUB = 185

@JsStatic internal const val ARM_INS_VIDUP = 186

@JsStatic internal const val ARM_INS_VIWDUP = 187

@JsStatic internal const val ARM_INS_VLD20 = 188

@JsStatic internal const val ARM_INS_VLD21 = 189

@JsStatic internal const val ARM_INS_VLD40 = 190

@JsStatic internal const val ARM_INS_VLD41 = 191

@JsStatic internal const val ARM_INS_VLD42 = 192

@JsStatic internal const val ARM_INS_VLD43 = 193

@JsStatic internal const val ARM_INS_VLDRB = 194

@JsStatic internal const val ARM_INS_VLDRD = 195

@JsStatic internal const val ARM_INS_VLDRH = 196

@JsStatic internal const val ARM_INS_VLDRW = 197

@JsStatic internal const val ARM_INS_VMAXAV = 198

@JsStatic internal const val ARM_INS_VMAXA = 199

@JsStatic internal const val ARM_INS_VMAXNMAV = 200

@JsStatic internal const val ARM_INS_VMAXNMA = 201

@JsStatic internal const val ARM_INS_VMAXNMV = 202

@JsStatic internal const val ARM_INS_VMAXNM = 203

@JsStatic internal const val ARM_INS_VMAXV = 204

@JsStatic internal const val ARM_INS_VMAX = 205

@JsStatic internal const val ARM_INS_VMINAV = 206

@JsStatic internal const val ARM_INS_VMINA = 207

@JsStatic internal const val ARM_INS_VMINNMAV = 208

@JsStatic internal const val ARM_INS_VMINNMA = 209

@JsStatic internal const val ARM_INS_VMINNMV = 210

@JsStatic internal const val ARM_INS_VMINNM = 211

@JsStatic internal const val ARM_INS_VMINV = 212

@JsStatic internal const val ARM_INS_VMIN = 213

@JsStatic internal const val ARM_INS_VMLADAVA = 214

@JsStatic internal const val ARM_INS_VMLADAVAX = 215

@JsStatic internal const val ARM_INS_VMLADAV = 216

@JsStatic internal const val ARM_INS_VMLADAVX = 217

@JsStatic internal const val ARM_INS_VMLALDAVA = 218

@JsStatic internal const val ARM_INS_VMLALDAVAX = 219

@JsStatic internal const val ARM_INS_VMLALDAV = 220

@JsStatic internal const val ARM_INS_VMLALDAVX = 221

@JsStatic internal const val ARM_INS_VMLAS = 222

@JsStatic internal const val ARM_INS_VMLA = 223

@JsStatic internal const val ARM_INS_VMLSDAVA = 224

@JsStatic internal const val ARM_INS_VMLSDAVAX = 225

@JsStatic internal const val ARM_INS_VMLSDAV = 226

@JsStatic internal const val ARM_INS_VMLSDAVX = 227

@JsStatic internal const val ARM_INS_VMLSLDAVA = 228

@JsStatic internal const val ARM_INS_VMLSLDAVAX = 229

@JsStatic internal const val ARM_INS_VMLSLDAV = 230

@JsStatic internal const val ARM_INS_VMLSLDAVX = 231

@JsStatic internal const val ARM_INS_VMOVLB = 232

@JsStatic internal const val ARM_INS_VMOVLT = 233

@JsStatic internal const val ARM_INS_VMOVNB = 234

@JsStatic internal const val ARM_INS_VMOVNT = 235

@JsStatic internal const val ARM_INS_VMULH = 236

@JsStatic internal const val ARM_INS_VMULLB = 237

@JsStatic internal const val ARM_INS_VMULLT = 238

@JsStatic internal const val ARM_INS_VMUL = 239

@JsStatic internal const val ARM_INS_VMVN = 240

@JsStatic internal const val ARM_INS_VNEG = 241

@JsStatic internal const val ARM_INS_VORN = 242

@JsStatic internal const val ARM_INS_VORR = 243

@JsStatic internal const val ARM_INS_VPNOT = 244

@JsStatic internal const val ARM_INS_VPSEL = 245

@JsStatic internal const val ARM_INS_VPST = 246

@JsStatic internal const val ARM_INS_VPT = 247

@JsStatic internal const val ARM_INS_VQABS = 248

@JsStatic internal const val ARM_INS_VQADD = 249

@JsStatic internal const val ARM_INS_VQDMLADHX = 250

@JsStatic internal const val ARM_INS_VQDMLADH = 251

@JsStatic internal const val ARM_INS_VQDMLAH = 252

@JsStatic internal const val ARM_INS_VQDMLASH = 253

@JsStatic internal const val ARM_INS_VQDMLSDHX = 254

@JsStatic internal const val ARM_INS_VQDMLSDH = 255

@JsStatic internal const val ARM_INS_VQDMULH = 256

@JsStatic internal const val ARM_INS_VQDMULLB = 257

@JsStatic internal const val ARM_INS_VQDMULLT = 258

@JsStatic internal const val ARM_INS_VQMOVNB = 259

@JsStatic internal const val ARM_INS_VQMOVNT = 260

@JsStatic internal const val ARM_INS_VQMOVUNB = 261

@JsStatic internal const val ARM_INS_VQMOVUNT = 262

@JsStatic internal const val ARM_INS_VQNEG = 263

@JsStatic internal const val ARM_INS_VQRDMLADHX = 264

@JsStatic internal const val ARM_INS_VQRDMLADH = 265

@JsStatic internal const val ARM_INS_VQRDMLAH = 266

@JsStatic internal const val ARM_INS_VQRDMLASH = 267

@JsStatic internal const val ARM_INS_VQRDMLSDHX = 268

@JsStatic internal const val ARM_INS_VQRDMLSDH = 269

@JsStatic internal const val ARM_INS_VQRDMULH = 270

@JsStatic internal const val ARM_INS_VQRSHL = 271

@JsStatic internal const val ARM_INS_VQRSHRNB = 272

@JsStatic internal const val ARM_INS_VQRSHRNT = 273

@JsStatic internal const val ARM_INS_VQRSHRUNB = 274

@JsStatic internal const val ARM_INS_VQRSHRUNT = 275

@JsStatic internal const val ARM_INS_VQSHLU = 276

@JsStatic internal const val ARM_INS_VQSHL = 277

@JsStatic internal const val ARM_INS_VQSHRNB = 278

@JsStatic internal const val ARM_INS_VQSHRNT = 279

@JsStatic internal const val ARM_INS_VQSHRUNB = 280

@JsStatic internal const val ARM_INS_VQSHRUNT = 281

@JsStatic internal const val ARM_INS_VQSUB = 282

@JsStatic internal const val ARM_INS_VREV16 = 283

@JsStatic internal const val ARM_INS_VREV32 = 284

@JsStatic internal const val ARM_INS_VREV64 = 285

@JsStatic internal const val ARM_INS_VRHADD = 286

@JsStatic internal const val ARM_INS_VRINTA = 287

@JsStatic internal const val ARM_INS_VRINTM = 288

@JsStatic internal const val ARM_INS_VRINTN = 289

@JsStatic internal const val ARM_INS_VRINTP = 290

@JsStatic internal const val ARM_INS_VRINTX = 291

@JsStatic internal const val ARM_INS_VRINTZ = 292

@JsStatic internal const val ARM_INS_VRMLALDAVHA = 293

@JsStatic internal const val ARM_INS_VRMLALDAVHAX = 294

@JsStatic internal const val ARM_INS_VRMLALDAVH = 295

@JsStatic internal const val ARM_INS_VRMLALDAVHX = 296

@JsStatic internal const val ARM_INS_VRMLSLDAVHA = 297

@JsStatic internal const val ARM_INS_VRMLSLDAVHAX = 298

@JsStatic internal const val ARM_INS_VRMLSLDAVH = 299

@JsStatic internal const val ARM_INS_VRMLSLDAVHX = 300

@JsStatic internal const val ARM_INS_VRMULH = 301

@JsStatic internal const val ARM_INS_VRSHL = 302

@JsStatic internal const val ARM_INS_VRSHRNB = 303

@JsStatic internal const val ARM_INS_VRSHRNT = 304

@JsStatic internal const val ARM_INS_VRSHR = 305

@JsStatic internal const val ARM_INS_VSBC = 306

@JsStatic internal const val ARM_INS_VSBCI = 307

@JsStatic internal const val ARM_INS_VSHLC = 308

@JsStatic internal const val ARM_INS_VSHLLB = 309

@JsStatic internal const val ARM_INS_VSHLLT = 310

@JsStatic internal const val ARM_INS_VSHL = 311

@JsStatic internal const val ARM_INS_VSHRNB = 312

@JsStatic internal const val ARM_INS_VSHRNT = 313

@JsStatic internal const val ARM_INS_VSHR = 314

@JsStatic internal const val ARM_INS_VSLI = 315

@JsStatic internal const val ARM_INS_VSRI = 316

@JsStatic internal const val ARM_INS_VST20 = 317

@JsStatic internal const val ARM_INS_VST21 = 318

@JsStatic internal const val ARM_INS_VST40 = 319

@JsStatic internal const val ARM_INS_VST41 = 320

@JsStatic internal const val ARM_INS_VST42 = 321

@JsStatic internal const val ARM_INS_VST43 = 322

@JsStatic internal const val ARM_INS_VSTRB = 323

@JsStatic internal const val ARM_INS_VSTRD = 324

@JsStatic internal const val ARM_INS_VSTRH = 325

@JsStatic internal const val ARM_INS_VSTRW = 326

@JsStatic internal const val ARM_INS_VSUB = 327

@JsStatic internal const val ARM_INS_WLSTP = 328

@JsStatic internal const val ARM_INS_MVN = 329

@JsStatic internal const val ARM_INS_ORR = 330

@JsStatic internal const val ARM_INS_PKHBT = 331

@JsStatic internal const val ARM_INS_PKHTB = 332

@JsStatic internal const val ARM_INS_PLDW = 333

@JsStatic internal const val ARM_INS_PLD = 334

@JsStatic internal const val ARM_INS_PLI = 335

@JsStatic internal const val ARM_INS_QADD = 336

@JsStatic internal const val ARM_INS_QADD16 = 337

@JsStatic internal const val ARM_INS_QADD8 = 338

@JsStatic internal const val ARM_INS_QASX = 339

@JsStatic internal const val ARM_INS_QDADD = 340

@JsStatic internal const val ARM_INS_QDSUB = 341

@JsStatic internal const val ARM_INS_QSAX = 342

@JsStatic internal const val ARM_INS_QSUB = 343

@JsStatic internal const val ARM_INS_QSUB16 = 344

@JsStatic internal const val ARM_INS_QSUB8 = 345

@JsStatic internal const val ARM_INS_RBIT = 346

@JsStatic internal const val ARM_INS_REV = 347

@JsStatic internal const val ARM_INS_REV16 = 348

@JsStatic internal const val ARM_INS_REVSH = 349

@JsStatic internal const val ARM_INS_RFEDA = 350

@JsStatic internal const val ARM_INS_RFEDB = 351

@JsStatic internal const val ARM_INS_RFEIA = 352

@JsStatic internal const val ARM_INS_RFEIB = 353

@JsStatic internal const val ARM_INS_RSB = 354

@JsStatic internal const val ARM_INS_RSC = 355

@JsStatic internal const val ARM_INS_SADD16 = 356

@JsStatic internal const val ARM_INS_SADD8 = 357

@JsStatic internal const val ARM_INS_SASX = 358

@JsStatic internal const val ARM_INS_SB = 359

@JsStatic internal const val ARM_INS_SBC = 360

@JsStatic internal const val ARM_INS_SBFX = 361

@JsStatic internal const val ARM_INS_SDIV = 362

@JsStatic internal const val ARM_INS_SEL = 363

@JsStatic internal const val ARM_INS_SETEND = 364

@JsStatic internal const val ARM_INS_SETPAN = 365

@JsStatic internal const val ARM_INS_SHA1C = 366

@JsStatic internal const val ARM_INS_SHA1H = 367

@JsStatic internal const val ARM_INS_SHA1M = 368

@JsStatic internal const val ARM_INS_SHA1P = 369

@JsStatic internal const val ARM_INS_SHA1SU0 = 370

@JsStatic internal const val ARM_INS_SHA1SU1 = 371

@JsStatic internal const val ARM_INS_SHA256H = 372

@JsStatic internal const val ARM_INS_SHA256H2 = 373

@JsStatic internal const val ARM_INS_SHA256SU0 = 374

@JsStatic internal const val ARM_INS_SHA256SU1 = 375

@JsStatic internal const val ARM_INS_SHADD16 = 376

@JsStatic internal const val ARM_INS_SHADD8 = 377

@JsStatic internal const val ARM_INS_SHASX = 378

@JsStatic internal const val ARM_INS_SHSAX = 379

@JsStatic internal const val ARM_INS_SHSUB16 = 380

@JsStatic internal const val ARM_INS_SHSUB8 = 381

@JsStatic internal const val ARM_INS_SMC = 382

@JsStatic internal const val ARM_INS_SMLABB = 383

@JsStatic internal const val ARM_INS_SMLABT = 384

@JsStatic internal const val ARM_INS_SMLAD = 385

@JsStatic internal const val ARM_INS_SMLADX = 386

@JsStatic internal const val ARM_INS_SMLAL = 387

@JsStatic internal const val ARM_INS_SMLALBB = 388

@JsStatic internal const val ARM_INS_SMLALBT = 389

@JsStatic internal const val ARM_INS_SMLALD = 390

@JsStatic internal const val ARM_INS_SMLALDX = 391

@JsStatic internal const val ARM_INS_SMLALTB = 392

@JsStatic internal const val ARM_INS_SMLALTT = 393

@JsStatic internal const val ARM_INS_SMLATB = 394

@JsStatic internal const val ARM_INS_SMLATT = 395

@JsStatic internal const val ARM_INS_SMLAWB = 396

@JsStatic internal const val ARM_INS_SMLAWT = 397

@JsStatic internal const val ARM_INS_SMLSD = 398

@JsStatic internal const val ARM_INS_SMLSDX = 399

@JsStatic internal const val ARM_INS_SMLSLD = 400

@JsStatic internal const val ARM_INS_SMLSLDX = 401

@JsStatic internal const val ARM_INS_SMMLA = 402

@JsStatic internal const val ARM_INS_SMMLAR = 403

@JsStatic internal const val ARM_INS_SMMLS = 404

@JsStatic internal const val ARM_INS_SMMLSR = 405

@JsStatic internal const val ARM_INS_SMMUL = 406

@JsStatic internal const val ARM_INS_SMMULR = 407

@JsStatic internal const val ARM_INS_SMUAD = 408

@JsStatic internal const val ARM_INS_SMUADX = 409

@JsStatic internal const val ARM_INS_SMULBB = 410

@JsStatic internal const val ARM_INS_SMULBT = 411

@JsStatic internal const val ARM_INS_SMULL = 412

@JsStatic internal const val ARM_INS_SMULTB = 413

@JsStatic internal const val ARM_INS_SMULTT = 414

@JsStatic internal const val ARM_INS_SMULWB = 415

@JsStatic internal const val ARM_INS_SMULWT = 416

@JsStatic internal const val ARM_INS_SMUSD = 417

@JsStatic internal const val ARM_INS_SMUSDX = 418

@JsStatic internal const val ARM_INS_SRSDA = 419

@JsStatic internal const val ARM_INS_SRSDB = 420

@JsStatic internal const val ARM_INS_SRSIA = 421

@JsStatic internal const val ARM_INS_SRSIB = 422

@JsStatic internal const val ARM_INS_SSAT = 423

@JsStatic internal const val ARM_INS_SSAT16 = 424

@JsStatic internal const val ARM_INS_SSAX = 425

@JsStatic internal const val ARM_INS_SSUB16 = 426

@JsStatic internal const val ARM_INS_SSUB8 = 427

@JsStatic internal const val ARM_INS_STC2L = 428

@JsStatic internal const val ARM_INS_STC2 = 429

@JsStatic internal const val ARM_INS_STCL = 430

@JsStatic internal const val ARM_INS_STC = 431

@JsStatic internal const val ARM_INS_STL = 432

@JsStatic internal const val ARM_INS_STLB = 433

@JsStatic internal const val ARM_INS_STLEX = 434

@JsStatic internal const val ARM_INS_STLEXB = 435

@JsStatic internal const val ARM_INS_STLEXD = 436

@JsStatic internal const val ARM_INS_STLEXH = 437

@JsStatic internal const val ARM_INS_STLH = 438

@JsStatic internal const val ARM_INS_STMDA = 439

@JsStatic internal const val ARM_INS_STMDB = 440

@JsStatic internal const val ARM_INS_STM = 441

@JsStatic internal const val ARM_INS_STMIB = 442

@JsStatic internal const val ARM_INS_STRD = 443

@JsStatic internal const val ARM_INS_STREX = 444

@JsStatic internal const val ARM_INS_STREXB = 445

@JsStatic internal const val ARM_INS_STREXD = 446

@JsStatic internal const val ARM_INS_STREXH = 447

@JsStatic internal const val ARM_INS_STRHT = 448

@JsStatic internal const val ARM_INS_SUB = 449

@JsStatic internal const val ARM_INS_SVC = 450

@JsStatic internal const val ARM_INS_SWP = 451

@JsStatic internal const val ARM_INS_SWPB = 452

@JsStatic internal const val ARM_INS_SXTAB = 453

@JsStatic internal const val ARM_INS_SXTAB16 = 454

@JsStatic internal const val ARM_INS_SXTAH = 455

@JsStatic internal const val ARM_INS_SXTB = 456

@JsStatic internal const val ARM_INS_SXTB16 = 457

@JsStatic internal const val ARM_INS_SXTH = 458

@JsStatic internal const val ARM_INS_TEQ = 459

@JsStatic internal const val ARM_INS_TRAP = 460

@JsStatic internal const val ARM_INS_TSB = 461

@JsStatic internal const val ARM_INS_TST = 462

@JsStatic internal const val ARM_INS_UADD16 = 463

@JsStatic internal const val ARM_INS_UADD8 = 464

@JsStatic internal const val ARM_INS_UASX = 465

@JsStatic internal const val ARM_INS_UBFX = 466

@JsStatic internal const val ARM_INS_UDF = 467

@JsStatic internal const val ARM_INS_UDIV = 468

@JsStatic internal const val ARM_INS_UHADD16 = 469

@JsStatic internal const val ARM_INS_UHADD8 = 470

@JsStatic internal const val ARM_INS_UHASX = 471

@JsStatic internal const val ARM_INS_UHSAX = 472

@JsStatic internal const val ARM_INS_UHSUB16 = 473

@JsStatic internal const val ARM_INS_UHSUB8 = 474

@JsStatic internal const val ARM_INS_UMAAL = 475

@JsStatic internal const val ARM_INS_UMLAL = 476

@JsStatic internal const val ARM_INS_UMULL = 477

@JsStatic internal const val ARM_INS_UQADD16 = 478

@JsStatic internal const val ARM_INS_UQADD8 = 479

@JsStatic internal const val ARM_INS_UQASX = 480

@JsStatic internal const val ARM_INS_UQSAX = 481

@JsStatic internal const val ARM_INS_UQSUB16 = 482

@JsStatic internal const val ARM_INS_UQSUB8 = 483

@JsStatic internal const val ARM_INS_USAD8 = 484

@JsStatic internal const val ARM_INS_USADA8 = 485

@JsStatic internal const val ARM_INS_USAT = 486

@JsStatic internal const val ARM_INS_USAT16 = 487

@JsStatic internal const val ARM_INS_USAX = 488

@JsStatic internal const val ARM_INS_USUB16 = 489

@JsStatic internal const val ARM_INS_USUB8 = 490

@JsStatic internal const val ARM_INS_UXTAB = 491

@JsStatic internal const val ARM_INS_UXTAB16 = 492

@JsStatic internal const val ARM_INS_UXTAH = 493

@JsStatic internal const val ARM_INS_UXTB = 494

@JsStatic internal const val ARM_INS_UXTB16 = 495

@JsStatic internal const val ARM_INS_UXTH = 496

@JsStatic internal const val ARM_INS_VABAL = 497

@JsStatic internal const val ARM_INS_VABA = 498

@JsStatic internal const val ARM_INS_VABDL = 499

@JsStatic internal const val ARM_INS_VACGE = 500

@JsStatic internal const val ARM_INS_VACGT = 501

@JsStatic internal const val ARM_INS_VADDHN = 502

@JsStatic internal const val ARM_INS_VADDL = 503

@JsStatic internal const val ARM_INS_VADDW = 504

@JsStatic internal const val ARM_INS_VFMAB = 505

@JsStatic internal const val ARM_INS_VFMAT = 506

@JsStatic internal const val ARM_INS_VBIF = 507

@JsStatic internal const val ARM_INS_VBIT = 508

@JsStatic internal const val ARM_INS_VBSL = 509

@JsStatic internal const val ARM_INS_VCEQ = 510

@JsStatic internal const val ARM_INS_VCGE = 511

@JsStatic internal const val ARM_INS_VCGT = 512

@JsStatic internal const val ARM_INS_VCLE = 513

@JsStatic internal const val ARM_INS_VCLT = 514

@JsStatic internal const val ARM_INS_VCMPE = 515

@JsStatic internal const val ARM_INS_VCNT = 516

@JsStatic internal const val ARM_INS_VDIV = 517

@JsStatic internal const val ARM_INS_VEXT = 518

@JsStatic internal const val ARM_INS_VFMAL = 519

@JsStatic internal const val ARM_INS_VFMSL = 520

@JsStatic internal const val ARM_INS_VFNMA = 521

@JsStatic internal const val ARM_INS_VFNMS = 522

@JsStatic internal const val ARM_INS_VINS = 523

@JsStatic internal const val ARM_INS_VJCVT = 524

@JsStatic internal const val ARM_INS_VLDMDB = 525

@JsStatic internal const val ARM_INS_VLDMIA = 526

@JsStatic internal const val ARM_INS_VLDR = 527

@JsStatic internal const val ARM_INS_VLLDM = 528

@JsStatic internal const val ARM_INS_VLSTM = 529

@JsStatic internal const val ARM_INS_VMLAL = 530

@JsStatic internal const val ARM_INS_VMLS = 531

@JsStatic internal const val ARM_INS_VMLSL = 532

@JsStatic internal const val ARM_INS_VMMLA = 533

@JsStatic internal const val ARM_INS_VMOVX = 534

@JsStatic internal const val ARM_INS_VMOVL = 535

@JsStatic internal const val ARM_INS_VMOVN = 536

@JsStatic internal const val ARM_INS_VMSR = 537

@JsStatic internal const val ARM_INS_VMULL = 538

@JsStatic internal const val ARM_INS_VNMLA = 539

@JsStatic internal const val ARM_INS_VNMLS = 540

@JsStatic internal const val ARM_INS_VNMUL = 541

@JsStatic internal const val ARM_INS_VPADAL = 542

@JsStatic internal const val ARM_INS_VPADDL = 543

@JsStatic internal const val ARM_INS_VPADD = 544

@JsStatic internal const val ARM_INS_VPMAX = 545

@JsStatic internal const val ARM_INS_VPMIN = 546

@JsStatic internal const val ARM_INS_VQDMLAL = 547

@JsStatic internal const val ARM_INS_VQDMLSL = 548

@JsStatic internal const val ARM_INS_VQDMULL = 549

@JsStatic internal const val ARM_INS_VQMOVUN = 550

@JsStatic internal const val ARM_INS_VQMOVN = 551

@JsStatic internal const val ARM_INS_VQRDMLSH = 552

@JsStatic internal const val ARM_INS_VQRSHRN = 553

@JsStatic internal const val ARM_INS_VQRSHRUN = 554

@JsStatic internal const val ARM_INS_VQSHRN = 555

@JsStatic internal const val ARM_INS_VQSHRUN = 556

@JsStatic internal const val ARM_INS_VRADDHN = 557

@JsStatic internal const val ARM_INS_VRECPE = 558

@JsStatic internal const val ARM_INS_VRECPS = 559

@JsStatic internal const val ARM_INS_VRINTR = 560

@JsStatic internal const val ARM_INS_VRSHRN = 561

@JsStatic internal const val ARM_INS_VRSQRTE = 562

@JsStatic internal const val ARM_INS_VRSQRTS = 563

@JsStatic internal const val ARM_INS_VRSRA = 564

@JsStatic internal const val ARM_INS_VRSUBHN = 565

@JsStatic internal const val ARM_INS_VSCCLRM = 566

@JsStatic internal const val ARM_INS_VSDOT = 567

@JsStatic internal const val ARM_INS_VSELEQ = 568

@JsStatic internal const val ARM_INS_VSELGE = 569

@JsStatic internal const val ARM_INS_VSELGT = 570

@JsStatic internal const val ARM_INS_VSELVS = 571

@JsStatic internal const val ARM_INS_VSHLL = 572

@JsStatic internal const val ARM_INS_VSHRN = 573

@JsStatic internal const val ARM_INS_VSMMLA = 574

@JsStatic internal const val ARM_INS_VSQRT = 575

@JsStatic internal const val ARM_INS_VSRA = 576

@JsStatic internal const val ARM_INS_VSTMDB = 577

@JsStatic internal const val ARM_INS_VSTMIA = 578

@JsStatic internal const val ARM_INS_VSTR = 579

@JsStatic internal const val ARM_INS_VSUBHN = 580

@JsStatic internal const val ARM_INS_VSUBL = 581

@JsStatic internal const val ARM_INS_VSUBW = 582

@JsStatic internal const val ARM_INS_VSUDOT = 583

@JsStatic internal const val ARM_INS_VSWP = 584

@JsStatic internal const val ARM_INS_VTBL = 585

@JsStatic internal const val ARM_INS_VTBX = 586

@JsStatic internal const val ARM_INS_VCVTR = 587

@JsStatic internal const val ARM_INS_VTRN = 588

@JsStatic internal const val ARM_INS_VTST = 589

@JsStatic internal const val ARM_INS_VUDOT = 590

@JsStatic internal const val ARM_INS_VUMMLA = 591

@JsStatic internal const val ARM_INS_VUSDOT = 592

@JsStatic internal const val ARM_INS_VUSMMLA = 593

@JsStatic internal const val ARM_INS_VUZP = 594

@JsStatic internal const val ARM_INS_VZIP = 595

@JsStatic internal const val ARM_INS_ADDW = 596

@JsStatic internal const val ARM_INS_AUT = 597

@JsStatic internal const val ARM_INS_AUTG = 598

@JsStatic internal const val ARM_INS_BFL = 599

@JsStatic internal const val ARM_INS_BFLX = 600

@JsStatic internal const val ARM_INS_BF = 601

@JsStatic internal const val ARM_INS_BFCSEL = 602

@JsStatic internal const val ARM_INS_BFX = 603

@JsStatic internal const val ARM_INS_BTI = 604

@JsStatic internal const val ARM_INS_BXAUT = 605

@JsStatic internal const val ARM_INS_CLRM = 606

@JsStatic internal const val ARM_INS_CSEL = 607

@JsStatic internal const val ARM_INS_CSINC = 608

@JsStatic internal const val ARM_INS_CSINV = 609

@JsStatic internal const val ARM_INS_CSNEG = 610

@JsStatic internal const val ARM_INS_DCPS1 = 611

@JsStatic internal const val ARM_INS_DCPS2 = 612

@JsStatic internal const val ARM_INS_DCPS3 = 613

@JsStatic internal const val ARM_INS_DLS = 614

@JsStatic internal const val ARM_INS_LE = 615

@JsStatic internal const val ARM_INS_ORN = 616

@JsStatic internal const val ARM_INS_PAC = 617

@JsStatic internal const val ARM_INS_PACBTI = 618

@JsStatic internal const val ARM_INS_PACG = 619

@JsStatic internal const val ARM_INS_SG = 620

@JsStatic internal const val ARM_INS_SUBS = 621

@JsStatic internal const val ARM_INS_SUBW = 622

@JsStatic internal const val ARM_INS_TBB = 623

@JsStatic internal const val ARM_INS_TBH = 624

@JsStatic internal const val ARM_INS_TT = 625

@JsStatic internal const val ARM_INS_TTA = 626

@JsStatic internal const val ARM_INS_TTAT = 627

@JsStatic internal const val ARM_INS_TTT = 628

@JsStatic internal const val ARM_INS_WLS = 629

@JsStatic internal const val ARM_INS_BLXNS = 630

@JsStatic internal const val ARM_INS_BXNS = 631

@JsStatic internal const val ARM_INS_CBNZ = 632

@JsStatic internal const val ARM_INS_CBZ = 633

@JsStatic internal const val ARM_INS_POP = 634

@JsStatic internal const val ARM_INS_PUSH = 635

@JsStatic internal const val ARM_INS___BRKDIV0 = 636

@JsStatic internal const val ARM_INS_ENDING = 637

@JsStatic internal const val ARM_INS_ALIAS_BEGIN = 638

@JsStatic internal const val ARM_INS_ALIAS_VMOV = 639

@JsStatic internal const val ARM_INS_ALIAS_NOP = 640

@JsStatic internal const val ARM_INS_ALIAS_VMLAVA = 662

@JsStatic internal const val ARM_INS_ALIAS_VRMLALVH = 663

@JsStatic internal const val ARM_INS_ALIAS_VRMLALVHA = 664

@JsStatic internal const val ARM_INS_ALIAS_VMLALV = 665

@JsStatic internal const val ARM_INS_ALIAS_VMLALVA = 666

@JsStatic internal const val ARM_INS_ALIAS_VBIC = 667

@JsStatic internal const val ARM_INS_ALIAS_VEOR = 668

@JsStatic internal const val ARM_INS_ALIAS_VORN = 669

@JsStatic internal const val ARM_INS_ALIAS_VORR = 670

@JsStatic internal const val ARM_INS_ALIAS_VAND = 671

@JsStatic internal const val ARM_INS_ALIAS_VPSEL = 672

@JsStatic internal const val ARM_INS_ALIAS_ERET = 673

@JsStatic internal const val ARM_INS_ALIAS_ASR = 674

@JsStatic internal const val ARM_INS_ALIAS_LSL = 675

@JsStatic internal const val ARM_INS_ALIAS_LSR = 676

@JsStatic internal const val ARM_INS_ALIAS_ROR = 677

@JsStatic internal const val ARM_INS_ALIAS_RRX = 678

@JsStatic internal const val ARM_INS_ALIAS_UXTW = 679

@JsStatic internal const val ARM_INS_ALIAS_LDM = 680

@JsStatic internal const val ARM_INS_ALIAS_POP = 681

@JsStatic internal const val ARM_INS_ALIAS_PUSH = 682

@JsStatic internal const val ARM_INS_ALIAS_POPW = 683

@JsStatic internal const val ARM_INS_ALIAS_PUSHW = 684

@JsStatic internal const val ARM_INS_ALIAS_VPOP = 685

@JsStatic internal const val ARM_INS_ALIAS_VPUSH = 686

@JsStatic internal const val ARM_INS_ALIAS_END = 687

@JsStatic internal const val ARM_FEATURE_INVALID = 0

@JsStatic internal const val ARM_FEATURE_JUMP = 1

@JsStatic internal const val ARM_FEATURE_CALL = 2

@JsStatic internal const val ARM_FEATURE_RET = 3

@JsStatic internal const val ARM_FEATURE_INT = 4

@JsStatic internal const val ARM_FEATURE_PRIVILEGE = 6

@JsStatic internal const val ARM_FEATURE_BRANCH_RELATIVE = 7

@JsStatic internal const val ARM_FEATURE_HASV4T = 128

@JsStatic internal const val ARM_FEATURE_HASV5T = 129

@JsStatic internal const val ARM_FEATURE_HASV5TE = 130

@JsStatic internal const val ARM_FEATURE_HASV6 = 131

@JsStatic internal const val ARM_FEATURE_HASV6M = 132

@JsStatic internal const val ARM_FEATURE_HASV8MBASELINE = 133

@JsStatic internal const val ARM_FEATURE_HASV8MMAINLINE = 134

@JsStatic internal const val ARM_FEATURE_HASV8_1MMAINLINE = 135

@JsStatic internal const val ARM_FEATURE_HASMVEINT = 136

@JsStatic internal const val ARM_FEATURE_HASMVEFLOAT = 137

@JsStatic internal const val ARM_FEATURE_HASCDE = 138

@JsStatic internal const val ARM_FEATURE_HASFPREGS = 139

@JsStatic internal const val ARM_FEATURE_HASFPREGS16 = 140

@JsStatic internal const val ARM_FEATURE_HASNOFPREGS16 = 141

@JsStatic internal const val ARM_FEATURE_HASFPREGS64 = 142

@JsStatic internal const val ARM_FEATURE_HASFPREGSV8_1M = 143

@JsStatic internal const val ARM_FEATURE_HASV6T2 = 144

@JsStatic internal const val ARM_FEATURE_HASV6K = 145

@JsStatic internal const val ARM_FEATURE_HASV7 = 146

@JsStatic internal const val ARM_FEATURE_HASV8 = 147

@JsStatic internal const val ARM_FEATURE_PREV8 = 148

@JsStatic internal const val ARM_FEATURE_HASV8_1A = 149

@JsStatic internal const val ARM_FEATURE_HASV8_2A = 150

@JsStatic internal const val ARM_FEATURE_HASV8_3A = 151

@JsStatic internal const val ARM_FEATURE_HASV8_4A = 152

@JsStatic internal const val ARM_FEATURE_HASV8_5A = 153

@JsStatic internal const val ARM_FEATURE_HASV8_6A = 154

@JsStatic internal const val ARM_FEATURE_HASV8_7A = 155

@JsStatic internal const val ARM_FEATURE_HASVFP2 = 156

@JsStatic internal const val ARM_FEATURE_HASVFP3 = 157

@JsStatic internal const val ARM_FEATURE_HASVFP4 = 158

@JsStatic internal const val ARM_FEATURE_HASDPVFP = 159

@JsStatic internal const val ARM_FEATURE_HASFPARMV8 = 160

@JsStatic internal const val ARM_FEATURE_HASNEON = 161

@JsStatic internal const val ARM_FEATURE_HASSHA2 = 162

@JsStatic internal const val ARM_FEATURE_HASAES = 163

@JsStatic internal const val ARM_FEATURE_HASCRYPTO = 164

@JsStatic internal const val ARM_FEATURE_HASDOTPROD = 165

@JsStatic internal const val ARM_FEATURE_HASCRC = 166

@JsStatic internal const val ARM_FEATURE_HASRAS = 167

@JsStatic internal const val ARM_FEATURE_HASLOB = 168

@JsStatic internal const val ARM_FEATURE_HASPACBTI = 169

@JsStatic internal const val ARM_FEATURE_HASFP16 = 170

@JsStatic internal const val ARM_FEATURE_HASFULLFP16 = 171

@JsStatic internal const val ARM_FEATURE_HASFP16FML = 172

@JsStatic internal const val ARM_FEATURE_HASBF16 = 173

@JsStatic internal const val ARM_FEATURE_HASMATMULINT8 = 174

@JsStatic internal const val ARM_FEATURE_HASDIVIDEINTHUMB = 175

@JsStatic internal const val ARM_FEATURE_HASDIVIDEINARM = 176

@JsStatic internal const val ARM_FEATURE_HASDSP = 177

@JsStatic internal const val ARM_FEATURE_HASDB = 178

@JsStatic internal const val ARM_FEATURE_HASDFB = 179

@JsStatic internal const val ARM_FEATURE_HASV7CLREX = 180

@JsStatic internal const val ARM_FEATURE_HASACQUIRERELEASE = 181

@JsStatic internal const val ARM_FEATURE_HASMP = 182

@JsStatic internal const val ARM_FEATURE_HASVIRTUALIZATION = 183

@JsStatic internal const val ARM_FEATURE_HASTRUSTZONE = 184

@JsStatic internal const val ARM_FEATURE_HAS8MSECEXT = 185

@JsStatic internal const val ARM_FEATURE_ISTHUMB = 186

@JsStatic internal const val ARM_FEATURE_ISTHUMB2 = 187

@JsStatic internal const val ARM_FEATURE_ISMCLASS = 188

@JsStatic internal const val ARM_FEATURE_ISNOTMCLASS = 189

@JsStatic internal const val ARM_FEATURE_ISARM = 190

@JsStatic internal const val ARM_FEATURE_USENACLTRAP = 191

@JsStatic internal const val ARM_FEATURE_USENEGATIVEIMMEDIATES = 192

@JsStatic internal const val ARM_FEATURE_HASSB = 193

@JsStatic internal const val ARM_FEATURE_HASCLRBHB = 194

@JsStatic internal const val ARM_FEATURE_ENDING = 195

@JsStatic internal const val ARM_MB_RESERVED_0 = 0

@JsStatic internal const val ARM_MB_OSHLD = 1

@JsStatic internal const val ARM_MB_OSHST = 2

@JsStatic internal const val ARM_MB_OSH = 3

@JsStatic internal const val ARM_MB_RESERVED_4 = 4

@JsStatic internal const val ARM_MB_NSHLD = 5

@JsStatic internal const val ARM_MB_NSHST = 6

@JsStatic internal const val ARM_MB_NSH = 7

@JsStatic internal const val ARM_MB_RESERVED_8 = 8

@JsStatic internal const val ARM_MB_ISHLD = 9

@JsStatic internal const val ARM_MB_ISHST = 10

@JsStatic internal const val ARM_MB_ISH = 11

@JsStatic internal const val ARM_MB_RESERVED_12 = 12

@JsStatic internal const val ARM_MB_LD = 13

@JsStatic internal const val ARM_MB_ST = 14

@JsStatic internal const val ARM_MB_SY = 15

@JsStatic internal const val ARM_OP_INVALID = 0

@JsStatic internal const val ARM_OP_REG = 1

@JsStatic internal const val ARM_OP_IMM = 2

@JsStatic internal const val ARM_OP_FP = 3

@JsStatic internal const val ARM_OP_PRED = 4

@JsStatic internal const val ARM_OP_CIMM = 16

@JsStatic internal const val ARM_OP_PIMM = 17

@JsStatic internal const val ARM_OP_SETEND = 18

@JsStatic internal const val ARM_OP_SYSREG = 19

@JsStatic internal const val ARM_OP_BANKEDREG = 20

@JsStatic internal const val ARM_OP_SPSR = 21

@JsStatic internal const val ARM_OP_CPSR = 22

@JsStatic internal const val ARM_OP_SYSM = 23

@JsStatic internal const val ARM_OP_VPRED_R = 24

@JsStatic internal const val ARM_OP_VPRED_N = 25

@JsStatic internal const val ARM_OP_MEM = 128

@JsStatic internal const val ARM_REG_INVALID = 0

@JsStatic internal const val ARM_REG_APSR = 1

@JsStatic internal const val ARM_REG_APSR_NZCV = 2

@JsStatic internal const val ARM_REG_CPSR = 3

@JsStatic internal const val ARM_REG_FPCXTNS = 4

@JsStatic internal const val ARM_REG_FPCXTS = 5

@JsStatic internal const val ARM_REG_FPEXC = 6

@JsStatic internal const val ARM_REG_FPINST = 7

@JsStatic internal const val ARM_REG_FPSCR = 8

@JsStatic internal const val ARM_REG_FPSCR_NZCV = 9

@JsStatic internal const val ARM_REG_FPSCR_NZCVQC = 10

@JsStatic internal const val ARM_REG_FPSID = 11

@JsStatic internal const val ARM_REG_ITSTATE = 12

@JsStatic internal const val ARM_REG_LR = 13

@JsStatic internal const val ARM_REG_PC = 14

@JsStatic internal const val ARM_REG_RA_AUTH_CODE = 15

@JsStatic internal const val ARM_REG_SP = 16

@JsStatic internal const val ARM_REG_SPSR = 17

@JsStatic internal const val ARM_REG_VPR = 18

@JsStatic internal const val ARM_REG_ZR = 19

@JsStatic internal const val ARM_REG_D0 = 20

@JsStatic internal const val ARM_REG_D1 = 21

@JsStatic internal const val ARM_REG_D2 = 22

@JsStatic internal const val ARM_REG_D3 = 23

@JsStatic internal const val ARM_REG_D4 = 24

@JsStatic internal const val ARM_REG_D5 = 25

@JsStatic internal const val ARM_REG_D6 = 26

@JsStatic internal const val ARM_REG_D7 = 27

@JsStatic internal const val ARM_REG_D8 = 28

@JsStatic internal const val ARM_REG_D9 = 29

@JsStatic internal const val ARM_REG_D10 = 30

@JsStatic internal const val ARM_REG_D11 = 31

@JsStatic internal const val ARM_REG_D12 = 32

@JsStatic internal const val ARM_REG_D13 = 33

@JsStatic internal const val ARM_REG_D14 = 34

@JsStatic internal const val ARM_REG_D15 = 35

@JsStatic internal const val ARM_REG_D16 = 36

@JsStatic internal const val ARM_REG_D17 = 37

@JsStatic internal const val ARM_REG_D18 = 38

@JsStatic internal const val ARM_REG_D19 = 39

@JsStatic internal const val ARM_REG_D20 = 40

@JsStatic internal const val ARM_REG_D21 = 41

@JsStatic internal const val ARM_REG_D22 = 42

@JsStatic internal const val ARM_REG_D23 = 43

@JsStatic internal const val ARM_REG_D24 = 44

@JsStatic internal const val ARM_REG_D25 = 45

@JsStatic internal const val ARM_REG_D26 = 46

@JsStatic internal const val ARM_REG_D27 = 47

@JsStatic internal const val ARM_REG_D28 = 48

@JsStatic internal const val ARM_REG_D29 = 49

@JsStatic internal const val ARM_REG_D30 = 50

@JsStatic internal const val ARM_REG_D31 = 51

@JsStatic internal const val ARM_REG_FPINST2 = 52

@JsStatic internal const val ARM_REG_MVFR0 = 53

@JsStatic internal const val ARM_REG_MVFR1 = 54

@JsStatic internal const val ARM_REG_MVFR2 = 55

@JsStatic internal const val ARM_REG_P0 = 56

@JsStatic internal const val ARM_REG_Q0 = 57

@JsStatic internal const val ARM_REG_Q1 = 58

@JsStatic internal const val ARM_REG_Q2 = 59

@JsStatic internal const val ARM_REG_Q3 = 60

@JsStatic internal const val ARM_REG_Q4 = 61

@JsStatic internal const val ARM_REG_Q5 = 62

@JsStatic internal const val ARM_REG_Q6 = 63

@JsStatic internal const val ARM_REG_Q7 = 64

@JsStatic internal const val ARM_REG_Q8 = 65

@JsStatic internal const val ARM_REG_Q9 = 66

@JsStatic internal const val ARM_REG_Q10 = 67

@JsStatic internal const val ARM_REG_Q11 = 68

@JsStatic internal const val ARM_REG_Q12 = 69

@JsStatic internal const val ARM_REG_Q13 = 70

@JsStatic internal const val ARM_REG_Q14 = 71

@JsStatic internal const val ARM_REG_Q15 = 72

@JsStatic internal const val ARM_REG_R0 = 73

@JsStatic internal const val ARM_REG_R1 = 74

@JsStatic internal const val ARM_REG_R2 = 75

@JsStatic internal const val ARM_REG_R3 = 76

@JsStatic internal const val ARM_REG_R4 = 77

@JsStatic internal const val ARM_REG_R5 = 78

@JsStatic internal const val ARM_REG_R6 = 79

@JsStatic internal const val ARM_REG_R7 = 80

@JsStatic internal const val ARM_REG_R8 = 81

@JsStatic internal const val ARM_REG_R9 = 82

@JsStatic internal const val ARM_REG_R10 = 83

@JsStatic internal const val ARM_REG_R11 = 84

@JsStatic internal const val ARM_REG_R12 = 85

@JsStatic internal const val ARM_REG_S0 = 86

@JsStatic internal const val ARM_REG_S1 = 87

@JsStatic internal const val ARM_REG_S2 = 88

@JsStatic internal const val ARM_REG_S3 = 89

@JsStatic internal const val ARM_REG_S4 = 90

@JsStatic internal const val ARM_REG_S5 = 91

@JsStatic internal const val ARM_REG_S6 = 92

@JsStatic internal const val ARM_REG_S7 = 93

@JsStatic internal const val ARM_REG_S8 = 94

@JsStatic internal const val ARM_REG_S9 = 95

@JsStatic internal const val ARM_REG_S10 = 96

@JsStatic internal const val ARM_REG_S11 = 97

@JsStatic internal const val ARM_REG_S12 = 98

@JsStatic internal const val ARM_REG_S13 = 99

@JsStatic internal const val ARM_REG_S14 = 100

@JsStatic internal const val ARM_REG_S15 = 101

@JsStatic internal const val ARM_REG_S16 = 102

@JsStatic internal const val ARM_REG_S17 = 103

@JsStatic internal const val ARM_REG_S18 = 104

@JsStatic internal const val ARM_REG_S19 = 105

@JsStatic internal const val ARM_REG_S20 = 106

@JsStatic internal const val ARM_REG_S21 = 107

@JsStatic internal const val ARM_REG_S22 = 108

@JsStatic internal const val ARM_REG_S23 = 109

@JsStatic internal const val ARM_REG_S24 = 110

@JsStatic internal const val ARM_REG_S25 = 111

@JsStatic internal const val ARM_REG_S26 = 112

@JsStatic internal const val ARM_REG_S27 = 113

@JsStatic internal const val ARM_REG_S28 = 114

@JsStatic internal const val ARM_REG_S29 = 115

@JsStatic internal const val ARM_REG_S30 = 116

@JsStatic internal const val ARM_REG_S31 = 117

@JsStatic internal const val ARM_REG_D0_D2 = 118

@JsStatic internal const val ARM_REG_D1_D3 = 119

@JsStatic internal const val ARM_REG_D2_D4 = 120

@JsStatic internal const val ARM_REG_D3_D5 = 121

@JsStatic internal const val ARM_REG_D4_D6 = 122

@JsStatic internal const val ARM_REG_D5_D7 = 123

@JsStatic internal const val ARM_REG_D6_D8 = 124

@JsStatic internal const val ARM_REG_D7_D9 = 125

@JsStatic internal const val ARM_REG_D8_D10 = 126

@JsStatic internal const val ARM_REG_D9_D11 = 127

@JsStatic internal const val ARM_REG_D10_D12 = 128

@JsStatic internal const val ARM_REG_D11_D13 = 129

@JsStatic internal const val ARM_REG_D12_D14 = 130

@JsStatic internal const val ARM_REG_D13_D15 = 131

@JsStatic internal const val ARM_REG_D14_D16 = 132

@JsStatic internal const val ARM_REG_D15_D17 = 133

@JsStatic internal const val ARM_REG_D16_D18 = 134

@JsStatic internal const val ARM_REG_D17_D19 = 135

@JsStatic internal const val ARM_REG_D18_D20 = 136

@JsStatic internal const val ARM_REG_D19_D21 = 137

@JsStatic internal const val ARM_REG_D20_D22 = 138

@JsStatic internal const val ARM_REG_D21_D23 = 139

@JsStatic internal const val ARM_REG_D22_D24 = 140

@JsStatic internal const val ARM_REG_D23_D25 = 141

@JsStatic internal const val ARM_REG_D24_D26 = 142

@JsStatic internal const val ARM_REG_D25_D27 = 143

@JsStatic internal const val ARM_REG_D26_D28 = 144

@JsStatic internal const val ARM_REG_D27_D29 = 145

@JsStatic internal const val ARM_REG_D28_D30 = 146

@JsStatic internal const val ARM_REG_D29_D31 = 147

@JsStatic internal const val ARM_REG_Q0_Q1 = 148

@JsStatic internal const val ARM_REG_Q1_Q2 = 149

@JsStatic internal const val ARM_REG_Q2_Q3 = 150

@JsStatic internal const val ARM_REG_Q3_Q4 = 151

@JsStatic internal const val ARM_REG_Q4_Q5 = 152

@JsStatic internal const val ARM_REG_Q5_Q6 = 153

@JsStatic internal const val ARM_REG_Q6_Q7 = 154

@JsStatic internal const val ARM_REG_Q7_Q8 = 155

@JsStatic internal const val ARM_REG_Q8_Q9 = 156

@JsStatic internal const val ARM_REG_Q9_Q10 = 157

@JsStatic internal const val ARM_REG_Q10_Q11 = 158

@JsStatic internal const val ARM_REG_Q11_Q12 = 159

@JsStatic internal const val ARM_REG_Q12_Q13 = 160

@JsStatic internal const val ARM_REG_Q13_Q14 = 161

@JsStatic internal const val ARM_REG_Q14_Q15 = 162

@JsStatic internal const val ARM_REG_Q0_Q1_Q2_Q3 = 163

@JsStatic internal const val ARM_REG_Q1_Q2_Q3_Q4 = 164

@JsStatic internal const val ARM_REG_Q2_Q3_Q4_Q5 = 165

@JsStatic internal const val ARM_REG_Q3_Q4_Q5_Q6 = 166

@JsStatic internal const val ARM_REG_Q4_Q5_Q6_Q7 = 167

@JsStatic internal const val ARM_REG_Q5_Q6_Q7_Q8 = 168

@JsStatic internal const val ARM_REG_Q6_Q7_Q8_Q9 = 169

@JsStatic internal const val ARM_REG_Q7_Q8_Q9_Q10 = 170

@JsStatic internal const val ARM_REG_Q8_Q9_Q10_Q11 = 171

@JsStatic internal const val ARM_REG_Q9_Q10_Q11_Q12 = 172

@JsStatic internal const val ARM_REG_Q10_Q11_Q12_Q13 = 173

@JsStatic internal const val ARM_REG_Q11_Q12_Q13_Q14 = 174

@JsStatic internal const val ARM_REG_Q12_Q13_Q14_Q15 = 175

@JsStatic internal const val ARM_REG_R0_R1 = 176

@JsStatic internal const val ARM_REG_R2_R3 = 177

@JsStatic internal const val ARM_REG_R4_R5 = 178

@JsStatic internal const val ARM_REG_R6_R7 = 179

@JsStatic internal const val ARM_REG_R8_R9 = 180

@JsStatic internal const val ARM_REG_R10_R11 = 181

@JsStatic internal const val ARM_REG_R12_SP = 182

@JsStatic internal const val ARM_REG_D0_D1_D2 = 183

@JsStatic internal const val ARM_REG_D1_D2_D3 = 184

@JsStatic internal const val ARM_REG_D2_D3_D4 = 185

@JsStatic internal const val ARM_REG_D3_D4_D5 = 186

@JsStatic internal const val ARM_REG_D4_D5_D6 = 187

@JsStatic internal const val ARM_REG_D5_D6_D7 = 188

@JsStatic internal const val ARM_REG_D6_D7_D8 = 189

@JsStatic internal const val ARM_REG_D7_D8_D9 = 190

@JsStatic internal const val ARM_REG_D8_D9_D10 = 191

@JsStatic internal const val ARM_REG_D9_D10_D11 = 192

@JsStatic internal const val ARM_REG_D10_D11_D12 = 193

@JsStatic internal const val ARM_REG_D11_D12_D13 = 194

@JsStatic internal const val ARM_REG_D12_D13_D14 = 195

@JsStatic internal const val ARM_REG_D13_D14_D15 = 196

@JsStatic internal const val ARM_REG_D14_D15_D16 = 197

@JsStatic internal const val ARM_REG_D15_D16_D17 = 198

@JsStatic internal const val ARM_REG_D16_D17_D18 = 199

@JsStatic internal const val ARM_REG_D17_D18_D19 = 200

@JsStatic internal const val ARM_REG_D18_D19_D20 = 201

@JsStatic internal const val ARM_REG_D19_D20_D21 = 202

@JsStatic internal const val ARM_REG_D20_D21_D22 = 203

@JsStatic internal const val ARM_REG_D21_D22_D23 = 204

@JsStatic internal const val ARM_REG_D22_D23_D24 = 205

@JsStatic internal const val ARM_REG_D23_D24_D25 = 206

@JsStatic internal const val ARM_REG_D24_D25_D26 = 207

@JsStatic internal const val ARM_REG_D25_D26_D27 = 208

@JsStatic internal const val ARM_REG_D26_D27_D28 = 209

@JsStatic internal const val ARM_REG_D27_D28_D29 = 210

@JsStatic internal const val ARM_REG_D28_D29_D30 = 211

@JsStatic internal const val ARM_REG_D29_D30_D31 = 212

@JsStatic internal const val ARM_REG_D0_D2_D4 = 213

@JsStatic internal const val ARM_REG_D1_D3_D5 = 214

@JsStatic internal const val ARM_REG_D2_D4_D6 = 215

@JsStatic internal const val ARM_REG_D3_D5_D7 = 216

@JsStatic internal const val ARM_REG_D4_D6_D8 = 217

@JsStatic internal const val ARM_REG_D5_D7_D9 = 218

@JsStatic internal const val ARM_REG_D6_D8_D10 = 219

@JsStatic internal const val ARM_REG_D7_D9_D11 = 220

@JsStatic internal const val ARM_REG_D8_D10_D12 = 221

@JsStatic internal const val ARM_REG_D9_D11_D13 = 222

@JsStatic internal const val ARM_REG_D10_D12_D14 = 223

@JsStatic internal const val ARM_REG_D11_D13_D15 = 224

@JsStatic internal const val ARM_REG_D12_D14_D16 = 225

@JsStatic internal const val ARM_REG_D13_D15_D17 = 226

@JsStatic internal const val ARM_REG_D14_D16_D18 = 227

@JsStatic internal const val ARM_REG_D15_D17_D19 = 228

@JsStatic internal const val ARM_REG_D16_D18_D20 = 229

@JsStatic internal const val ARM_REG_D17_D19_D21 = 230

@JsStatic internal const val ARM_REG_D18_D20_D22 = 231

@JsStatic internal const val ARM_REG_D19_D21_D23 = 232

@JsStatic internal const val ARM_REG_D20_D22_D24 = 233

@JsStatic internal const val ARM_REG_D21_D23_D25 = 234

@JsStatic internal const val ARM_REG_D22_D24_D26 = 235

@JsStatic internal const val ARM_REG_D23_D25_D27 = 236

@JsStatic internal const val ARM_REG_D24_D26_D28 = 237

@JsStatic internal const val ARM_REG_D25_D27_D29 = 238

@JsStatic internal const val ARM_REG_D26_D28_D30 = 239

@JsStatic internal const val ARM_REG_D27_D29_D31 = 240

@JsStatic internal const val ARM_REG_D0_D2_D4_D6 = 241

@JsStatic internal const val ARM_REG_D1_D3_D5_D7 = 242

@JsStatic internal const val ARM_REG_D2_D4_D6_D8 = 243

@JsStatic internal const val ARM_REG_D3_D5_D7_D9 = 244

@JsStatic internal const val ARM_REG_D4_D6_D8_D10 = 245

@JsStatic internal const val ARM_REG_D5_D7_D9_D11 = 246

@JsStatic internal const val ARM_REG_D6_D8_D10_D12 = 247

@JsStatic internal const val ARM_REG_D7_D9_D11_D13 = 248

@JsStatic internal const val ARM_REG_D8_D10_D12_D14 = 249

@JsStatic internal const val ARM_REG_D9_D11_D13_D15 = 250

@JsStatic internal const val ARM_REG_D10_D12_D14_D16 = 251

@JsStatic internal const val ARM_REG_D11_D13_D15_D17 = 252

@JsStatic internal const val ARM_REG_D12_D14_D16_D18 = 253

@JsStatic internal const val ARM_REG_D13_D15_D17_D19 = 254

@JsStatic internal const val ARM_REG_D14_D16_D18_D20 = 255

@JsStatic internal const val ARM_REG_D15_D17_D19_D21 = 256

@JsStatic internal const val ARM_REG_D16_D18_D20_D22 = 257

@JsStatic internal const val ARM_REG_D17_D19_D21_D23 = 258

@JsStatic internal const val ARM_REG_D18_D20_D22_D24 = 259

@JsStatic internal const val ARM_REG_D19_D21_D23_D25 = 260

@JsStatic internal const val ARM_REG_D20_D22_D24_D26 = 261

@JsStatic internal const val ARM_REG_D21_D23_D25_D27 = 262

@JsStatic internal const val ARM_REG_D22_D24_D26_D28 = 263

@JsStatic internal const val ARM_REG_D23_D25_D27_D29 = 264

@JsStatic internal const val ARM_REG_D24_D26_D28_D30 = 265

@JsStatic internal const val ARM_REG_D25_D27_D29_D31 = 266

@JsStatic internal const val ARM_REG_D1_D2 = 267

@JsStatic internal const val ARM_REG_D3_D4 = 268

@JsStatic internal const val ARM_REG_D5_D6 = 269

@JsStatic internal const val ARM_REG_D7_D8 = 270

@JsStatic internal const val ARM_REG_D9_D10 = 271

@JsStatic internal const val ARM_REG_D11_D12 = 272

@JsStatic internal const val ARM_REG_D13_D14 = 273

@JsStatic internal const val ARM_REG_D15_D16 = 274

@JsStatic internal const val ARM_REG_D17_D18 = 275

@JsStatic internal const val ARM_REG_D19_D20 = 276

@JsStatic internal const val ARM_REG_D21_D22 = 277

@JsStatic internal const val ARM_REG_D23_D24 = 278

@JsStatic internal const val ARM_REG_D25_D26 = 279

@JsStatic internal const val ARM_REG_D27_D28 = 280

@JsStatic internal const val ARM_REG_D29_D30 = 281

@JsStatic internal const val ARM_REG_D1_D2_D3_D4 = 282

@JsStatic internal const val ARM_REG_D3_D4_D5_D6 = 283

@JsStatic internal const val ARM_REG_D5_D6_D7_D8 = 284

@JsStatic internal const val ARM_REG_D7_D8_D9_D10 = 285

@JsStatic internal const val ARM_REG_D9_D10_D11_D12 = 286

@JsStatic internal const val ARM_REG_D11_D12_D13_D14 = 287

@JsStatic internal const val ARM_REG_D13_D14_D15_D16 = 288

@JsStatic internal const val ARM_REG_D15_D16_D17_D18 = 289

@JsStatic internal const val ARM_REG_D17_D18_D19_D20 = 290

@JsStatic internal const val ARM_REG_D19_D20_D21_D22 = 291

@JsStatic internal const val ARM_REG_D21_D22_D23_D24 = 292

@JsStatic internal const val ARM_REG_D23_D24_D25_D26 = 293

@JsStatic internal const val ARM_REG_D25_D26_D27_D28 = 294

@JsStatic internal const val ARM_REG_D27_D28_D29_D30 = 295

@JsStatic internal const val ARM_REG_ENDING = 296

@JsStatic internal const val ARM_REG_R13 = 16

@JsStatic internal const val ARM_REG_R14 = 13

@JsStatic internal const val ARM_REG_R15 = 14

@JsStatic internal const val ARM_REG_SB = 82

@JsStatic internal const val ARM_REG_SL = 83

@JsStatic internal const val ARM_REG_FP = 84

@JsStatic internal const val ARM_REG_IP = 85

@JsStatic internal const val ARM_SETEND_INVALID = 0

@JsStatic internal const val ARM_SETEND_BE = 1

@JsStatic internal const val ARM_SETEND_LE = 2

@JsStatic internal const val ARM_SFT_INVALID = 0

@JsStatic internal const val ARM_SFT_ASR = 1

@JsStatic internal const val ARM_SFT_LSL = 2

@JsStatic internal const val ARM_SFT_LSR = 3

@JsStatic internal const val ARM_SFT_ROR = 4

@JsStatic internal const val ARM_SFT_RRX = 5

@JsStatic internal const val ARM_SFT_UXTW = 6

@JsStatic internal const val ARM_SFT_REG = 7

@JsStatic internal const val ARM_SFT_ASR_REG = 8

@JsStatic internal const val ARM_SFT_LSL_REG = 9

@JsStatic internal const val ARM_SFT_LSR_REG = 10

@JsStatic internal const val ARM_SFT_ROR_REG = 11

@JsStatic internal const val ARM_FIELD_SPSR_C = 1

@JsStatic internal const val ARM_FIELD_SPSR_X = 2

@JsStatic internal const val ARM_FIELD_SPSR_S = 4

@JsStatic internal const val ARM_FIELD_SPSR_F = 8

@JsStatic internal const val ARM_FIELD_CPSR_C = 16

@JsStatic internal const val ARM_FIELD_CPSR_X = 32

@JsStatic internal const val ARM_FIELD_CPSR_S = 64

@JsStatic internal const val ARM_FIELD_CPSR_F = 128

@JsStatic internal const val ARM_MCLASSSYSREG_APSR = 2048

@JsStatic internal const val ARM_MCLASSSYSREG_APSR_G = 1024

@JsStatic internal const val ARM_MCLASSSYSREG_APSR_NZCVQ = 2048

@JsStatic internal const val ARM_MCLASSSYSREG_APSR_NZCVQG = 3072

@JsStatic internal const val ARM_MCLASSSYSREG_BASEPRI = 2065

@JsStatic internal const val ARM_MCLASSSYSREG_BASEPRI_MAX = 2066

@JsStatic internal const val ARM_MCLASSSYSREG_BASEPRI_NS = 2193

@JsStatic internal const val ARM_MCLASSSYSREG_CONTROL = 2068

@JsStatic internal const val ARM_MCLASSSYSREG_CONTROL_NS = 2196

@JsStatic internal const val ARM_MCLASSSYSREG_EAPSR = 2050

@JsStatic internal const val ARM_MCLASSSYSREG_EAPSR_G = 1026

@JsStatic internal const val ARM_MCLASSSYSREG_EAPSR_NZCVQ = 2050

@JsStatic internal const val ARM_MCLASSSYSREG_EAPSR_NZCVQG = 3074

@JsStatic internal const val ARM_MCLASSSYSREG_EPSR = 2054

@JsStatic internal const val ARM_MCLASSSYSREG_FAULTMASK = 2067

@JsStatic internal const val ARM_MCLASSSYSREG_FAULTMASK_NS = 2195

@JsStatic internal const val ARM_MCLASSSYSREG_IAPSR = 2049

@JsStatic internal const val ARM_MCLASSSYSREG_IAPSR_G = 1025

@JsStatic internal const val ARM_MCLASSSYSREG_IAPSR_NZCVQ = 2049

@JsStatic internal const val ARM_MCLASSSYSREG_IAPSR_NZCVQG = 3073

@JsStatic internal const val ARM_MCLASSSYSREG_IEPSR = 2055

@JsStatic internal const val ARM_MCLASSSYSREG_IPSR = 2053

@JsStatic internal const val ARM_MCLASSSYSREG_MSP = 2056

@JsStatic internal const val ARM_MCLASSSYSREG_MSPLIM = 2058

@JsStatic internal const val ARM_MCLASSSYSREG_MSPLIM_NS = 2186

@JsStatic internal const val ARM_MCLASSSYSREG_MSP_NS = 2184

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_P_0 = 2080

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_P_0_NS = 2208

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_P_1 = 2081

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_P_1_NS = 2209

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_P_2 = 2082

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_P_2_NS = 2210

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_P_3 = 2083

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_P_3_NS = 2211

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_U_0 = 2084

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_U_0_NS = 2212

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_U_1 = 2085

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_U_1_NS = 2213

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_U_2 = 2086

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_U_2_NS = 2214

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_U_3 = 2087

@JsStatic internal const val ARM_MCLASSSYSREG_PAC_KEY_U_3_NS = 2215

@JsStatic internal const val ARM_MCLASSSYSREG_PRIMASK = 2064

@JsStatic internal const val ARM_MCLASSSYSREG_PRIMASK_NS = 2192

@JsStatic internal const val ARM_MCLASSSYSREG_PSP = 2057

@JsStatic internal const val ARM_MCLASSSYSREG_PSPLIM = 2059

@JsStatic internal const val ARM_MCLASSSYSREG_PSPLIM_NS = 2187

@JsStatic internal const val ARM_MCLASSSYSREG_PSP_NS = 2185

@JsStatic internal const val ARM_MCLASSSYSREG_SP_NS = 2200

@JsStatic internal const val ARM_MCLASSSYSREG_XPSR = 2051

@JsStatic internal const val ARM_MCLASSSYSREG_XPSR_G = 1027

@JsStatic internal const val ARM_MCLASSSYSREG_XPSR_NZCVQ = 2051

@JsStatic internal const val ARM_MCLASSSYSREG_XPSR_NZCVQG = 3075

@JsStatic internal const val ARM_VECTORDATA_INVALID = 0

@JsStatic internal const val ARM_VECTORDATA_I8 = 1

@JsStatic internal const val ARM_VECTORDATA_I16 = 2

@JsStatic internal const val ARM_VECTORDATA_I32 = 3

@JsStatic internal const val ARM_VECTORDATA_I64 = 4

@JsStatic internal const val ARM_VECTORDATA_S8 = 5

@JsStatic internal const val ARM_VECTORDATA_S16 = 6

@JsStatic internal const val ARM_VECTORDATA_S32 = 7

@JsStatic internal const val ARM_VECTORDATA_S64 = 8

@JsStatic internal const val ARM_VECTORDATA_U8 = 9

@JsStatic internal const val ARM_VECTORDATA_U16 = 10

@JsStatic internal const val ARM_VECTORDATA_U32 = 11

@JsStatic internal const val ARM_VECTORDATA_U64 = 12

@JsStatic internal const val ARM_VECTORDATA_P8 = 13

@JsStatic internal const val ARM_VECTORDATA_P16 = 14

@JsStatic internal const val ARM_VECTORDATA_F16 = 15

@JsStatic internal const val ARM_VECTORDATA_F32 = 16

@JsStatic internal const val ARM_VECTORDATA_F64 = 17

@JsStatic internal const val ARM_VECTORDATA_F16F64 = 18

@JsStatic internal const val ARM_VECTORDATA_F64F16 = 19

@JsStatic internal const val ARM_VECTORDATA_F32F16 = 20

@JsStatic internal const val ARM_VECTORDATA_F16F32 = 21

@JsStatic internal const val ARM_VECTORDATA_F64F32 = 22

@JsStatic internal const val ARM_VECTORDATA_F32F64 = 23

@JsStatic internal const val ARM_VECTORDATA_S32F32 = 24

@JsStatic internal const val ARM_VECTORDATA_U32F32 = 25

@JsStatic internal const val ARM_VECTORDATA_F32S32 = 26

@JsStatic internal const val ARM_VECTORDATA_F32U32 = 27

@JsStatic internal const val ARM_VECTORDATA_F64S16 = 28

@JsStatic internal const val ARM_VECTORDATA_F32S16 = 29

@JsStatic internal const val ARM_VECTORDATA_F64S32 = 30

@JsStatic internal const val ARM_VECTORDATA_S16F64 = 31

@JsStatic internal const val ARM_VECTORDATA_S16F32 = 32

@JsStatic internal const val ARM_VECTORDATA_S32F64 = 33

@JsStatic internal const val ARM_VECTORDATA_U16F64 = 34

@JsStatic internal const val ARM_VECTORDATA_U16F32 = 35

@JsStatic internal const val ARM_VECTORDATA_U32F64 = 36

@JsStatic internal const val ARM_VECTORDATA_F64U16 = 37

@JsStatic internal const val ARM_VECTORDATA_F32U16 = 38

@JsStatic internal const val ARM_VECTORDATA_F64U32 = 39

@JsStatic internal const val ARM_VECTORDATA_F16U16 = 40

@JsStatic internal const val ARM_VECTORDATA_U16F16 = 41

@JsStatic internal const val ARM_VECTORDATA_F16U32 = 42

@JsStatic internal const val ARM_VECTORDATA_U32F16 = 43

@JsStatic internal const val ARM_VECTORDATA_F16S16 = 44

@JsStatic internal const val ARM_VECTORDATA_S16F16 = 45

@JsStatic internal const val ARM_VECTORDATA_F16S32 = 46

@JsStatic internal const val ARM_VECTORDATA_S32F16 = 47
