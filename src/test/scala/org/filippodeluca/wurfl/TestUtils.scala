/*
 * Copyright 2012.
 *   Filippo De Luca <me@filippodeluca.com>
 *   Fantayeneh Asres Gizaw <fantayeneh@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.filippodeluca.wurfl

import io.Source


object TestUtils {

  val testEntries: Map[String, Seq[String]] = Map(
    "Nokia7210/1.0 (2.01) Profile/MIDP-1.0 Configuration/CLDC-1.0"->Seq("nokia_7210_ver1_sub192"),
    "Nokia7250/la supercazzola prematurata 2.5"->Seq("nokia_7250_ver1_sub205"),

    // Mozilled Nokia
    "Mozilla/5.0 (Symbianos/9.2; U; Series60/3.1 NokiaN95/10.0.014; Profile/MIDP-2.0 Configuration/CLDC-1.1) AppleWebKit/413 (KHTML, like Gecko) Safari/413"->Seq("nokia_n95_ver1_sub100014"),
    "Mozilla/5.0 (Symbianos/9.2; U; Series60/3.1 NokiaN95/10.0.395; Profile/MIDP-2.0 Configuration/CLDC-1.1) AppleWebKit/413 (KHTML, like Gecko) Safari/413"->Seq("nokia_n95_ver1_sub100014"),
    "Mozilla/5.0 (SymbianOS/9.1; U; en-us) AppleWebKit/413 (KHTML, like Gecko) Safari/413"->Seq("nokia_n73_ver1_submozilla50", "nokia_n80_ver1_submozilla50"),
    "Mozilla/5.0 (SymbianOS/9.1; U; en-us) AppleWebKit/414 (KHTML, like Gecko) Safari/414 es61"->Seq("nokia_e61_ver1_submozilla50_sub2"),
    "Mozilla/5.0 (SymbianOS/9.1; U; en-us) AppleWebKit/413 (KHTML, like Gecko) Safari/413 es65"->Seq("nokia_e65_ver1_subsafari413"),
    "Mozilla/5.0 (SymbianOS/9.2; U; Series60/3.1 Nokia6110Navigator/3.48; Profile/MIDP-2.0 Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML, like Gecko) Safari/413"->Seq("nokia_6110nav_ver1_submoz"),
    "Mozilla/5.0 (SymbianOS/9.2; U; Series60/3.1 Nokia6110Navigator/3.58; Profile/MIDP-2.0 Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML, like Gecko) Safari/413"->Seq("nokia_6110nav_ver1_submoz"),

    // Sony Ericsson
    "SonyEricssonK510i/R4CA Browser/NetFront/3.3 Profile/MIDP-2.0 Configuration/CLDC-1.1"->Seq("sonyericsson_k510i_ver1_subr4ch"),
    "SonyEricssonK510i/CW34/ Kurazza Profile/MIDP-2.0 Configuration/CLDC-1.1"->Seq("sonyericsson_k510i_ver1_subr4ch"),

    // Motorola
    "Motorola-C290 Obigo/DRF-PU73 MMP/2.0 Profile/MIDP-2.0 Configuration/CLDC-1.1"->Seq("motorola_c290_ver1_sub1"),
    "Motorola-MPX200/RF-PU73D MMP/2.0 Profile/MIDP-2.0 Configuration/CLDC-1.1"->Seq("spv_ver1_submotompx200"),
    "Mot-v361/02.g7.821 Mib/2.2.1 Profile/midp-2.0 Configuration/cldc-1.1"->Seq("motorola_v361_ver1_sub08b786r"),
    "MOT-A1200/D22  Mozilla/4.0(compatible; MSIE 6.0; Lonax; Motorola A1200; 1862) Profile/MIDP-2.0 Configuration/CLDC 1.1 Opera 8.00 [en]"->Seq("mot_ming_ver1_subr532g110053p"),
    "MOT-Timeport260GPRS/ MMP/2.0 Profile/MIDP-2.0 Configuration/CLDC-1.1"->Seq("mot_timeport_260gpr_ver1"),
    "MOT-BC"->Seq("mot_bc_ver1_sub4123c"),

    // BlackBerry
    "BlackBerry8800/4.2.1 Profile/MIDP-2.0 Configuration/CLDC-1.1 VendorID/134"->Seq("blackberry8800_ver1_sub421102"),
    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0) BlackBerry8800/4.2.1 Profile/MIDP-2.0 Configuration/CLDC-1.1 VendorID/115"->Seq("blackberry8800_ver1_sub421102"),
    "BlackBerry8800/4.2.1 Profile/MIDP-2.0 Configuration/CLDC-1.1 VendorID/107 UP.Browser/5.0.3.3 UP.Link/5.1.2.1"->Seq("blackberry8800_ver1_sub421102"),

    // Ericsson
    "EricssonT39/R202 Profile/MIDP-2.0 Configuration/CLDC-1.1"->Seq("ericsson_t39_ver1_subr202"),

    // Siemens
    "SIE-2128/13 UP.Browser/5.0.3.3 (GUI)"->Seq("sie_2128_ver1_sub11", "sie_2128_ver1_sub12"),
    "SIE-3118/09 UP.Browser/5.0.3.3 (GUI)"->Seq("sie_3118_ver1_sub17"),

    // Sagem
    "SAGEM-my3022/R202 Profile/MIDP-2.0 Configuration/CLDC-1.1"->Seq("sagem_my3022_ver1"),
    "Sagem-my212X/Mirage1.0 UP.Browser/5.0.5.6 (GUI)"->Seq("sagem_my212x_ver1_sub5056"),

    // Samsung
    "SEC-SGHM300/1.0 Openwave/6.2.5 Profile/MIDP-2.0 Configuration/CLDC-1.1 UP.Browser/6.2.3.5.c.2.101 (GUI) MMP/2.0"->Seq("sec_sghm300_ver1"),
    "Samsung-SPHA680 UP.Browser/6.2.0 MMP/2.0"->Seq("samsung_spha680_ver1_sub20"),
    "SAMSUNG-SGH-C160/2.2.0 NetFront/3.0.22.2.3 (GUI) MMP/2.0"->Seq("samsung_sgh_c160_ver1"),
    "SPH-E119 Profile/MIDP-2.0 Configuration/CLDC-1.1 UP.Browser/6.2.2.5 (GUI) MMP/2.0"->Seq("sph_e119_ver1_sub20"),
    "SCH-U620/2.0 NetFront/3.0.22.2.3 (GUI) MMP/2.0"->Seq("samsung_u620_verizon_sub10"),
    "SGH-Z107 SHP/VPP/R5 SMB3.1 DMM-MMS/1.1.0 profile/MIDP-2.0 configuration/CLDC-1.0"->Seq("samsung_z107_ver1_subsmb31"),
    "SCH-W579 Profile/MIDP-2.0 Configuration/CLDC-1.1 UP.Browser/6.2.3.8 (GUI) MMP/2.0"->Seq("samsung_w579_ver1"),
    "SAMSUNG/SGHE860V/2.4/1.0 Browser/NF3/3.2 Profile/MIDP-2.0 Configuration/CLDC-1.1"->Seq("samsung_e860_ver1_sub10"),

    // Panasonic
    "Panasonic-GAD35/1.1 UP.Browser/4.1.20a"->Seq("panasonic_GD35_ver1_sub4124d"),

    // Nec
    "NEC-e353/001.00 ACS-NF/5.2 Profile/MIDP-2.0 Configuration/CLDC-1.1"->Seq("nec_e353_ver1_sub10"),
    "NEC-e353/1.23 ACS-NF/3.2 Profile/MIDP-2.0 Configuration/CLDC-1.1 Qtv/1.0"->Seq("nec_e353_ver1_sub10"),
    "KGT/2.1 N590i-10(c10;TB)"->Seq("kgt_ver1_subn590i"),

    //Qtek
    "Qtek8010 (Mozilla/4.0 compatible; MSIE 4.02; Windows CE; Smartphone; 176x220)"->Seq("qtek_8010_ver1_sub401"),

    // Mitsubishi
    "Mitsu/1.3.B (M350)"->Seq("mitsu_mt350_ver1"),

    // Philips
    "PHILIPS-Az@lis288 UP/4.1.236 UP.Browser/4.1.236-XXXX"->Seq("philips_azlis288_ver1_sub4119", "philips_azlis288_ver1_sub4119l", "philips_azlis288_ver1_sub41191xxxx", "philips_azlis288_ver1"),
    "PHILIPS568/2.0 UP.Browser/6.1.0.7.8 (GUI) MMP/1.0"->Seq("philips_568_ver1_sub61078"),
    "Philips-568 UP.Browser/6.2.0.7.4 (GUI) MMP/1.0"->Seq("philips_568_ver1_sub10"),

    // LG
    "LG500/v1.46 AU/2.0"->Seq("lg_500_ver1_sub20"),
    "LG-B2000 MIC/WAP2.0 MIDP-2.0/CLDC-1.0"->Seq("lg_b2000_ver1_subaumic20"),
    "LGE-CU6260/2.0 UP.Browser/4.1.26l"->Seq("lge_cu6260_ver1_sub4126i"),
    "LGE/U8150/2.0 Profile/MIDP-2.0 Configuration/CLDC-1.0"->Seq("lge_u8150_ver1_submidp20"),
    "LG/U250/v1.0 Profile/MIDP-1.0 Configuration/CLDC-1.0"->Seq("lg_u250_ver1"),

    // Apple
    "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1B834a Safari/420.4"->Seq("apple_iphone_ver1"),

    // Kyocera
    "kyocera-KZ-820/1.0 UP.Browser/4.2.3a"->Seq("kyocera_kz820_ver1_sub41264"),
    "QC-7135/1.0.17 UP.Browser/4.1.22b1"->Seq("kyocera_7135_ver1_sub1038"),
    "KWC-K323/1.0.13 UP.Browser/6.2.3.9.g.1.100 (GUI) MMP/2.0"->Seq("kyocera_k323_ver1_sub6239g1100"),

    // Alcatel
    "Alcatel-BE3/1.2 UP/4.1.8d UP.Browser/4.1.8d-XXXX"->Seq("alcatel_be3_ver1"),
    "ALCATEL-OH1/1.2 UP/4.1.8d UP.Browser/4.1.8d-XXXX"->Seq("alcatel_ot156_ver1"),

    // Sharp
    "SHARP-TM-100/1.0 Profile/MIDP-1.0 Configuration/CLDC-1.0 UP.Browser/6.2.3.8 (GUI) MMP/1.0"->Seq("sharptm_100_ver1_sub6226"),
    "SHARP-TM-150/Uncontrolled,Cust-99 Profile/MIDP-1.0 Configuration/CLDC-1.0 UP.Browser/6.2.2.6.c.2.103 (GUI) MMP/1.0"->Seq("sharp_tm_150_ver1_subuncontrolled"),
    "SHARP-TQ-GX-21/1.0 Profile/MIDP-1.0 Configuration/CLDC-1.0 UP.Browser/6.2.2.4.163 (GUI) MMP/1.0"->Seq("sharp_tq_gx_21_ver1_sub6224163"),
    "SHARP-TQ-GX-21/2.0 Profile/MIDP-1.0 Configuration/CLDC-1.0 UP.Browser/6.2.2.4.163 (GUI) MMP/1.0"->Seq("sharp_tq_gx_21_ver1_sub6222107", "sharp_tq_gx_21_ver1_sub6224163", "sharp_tq_gx_21_ver1"),
    "SharpT71/SHS002/1.0 Browser/UP.Browser/7.0.2.1a.f.1.104 (GUI) Profile/MIDP-2.0 UP.Browser/7.0.2.3.119 (GUI) MMP/2.0 Push/PO"->Seq("sharp_t71_ver1_sub7023119"),
    "SharpWXT71/SHS002/1.0 Browser/UP.Browser/7.0.2.1a.f.1.104 (GUI) Profile/MIDP-2.0 UP.Browser/7.0.2.3.119 (GUI) MMP/2.0 Push/PO"->Seq("sharp_wxt71_ver1_sub7023119"),

    "Vodafone/SonyEricssonV800/R1S025/SN348797463849932 Browser/SEMC-Browser/4.1 Profile/MIDP-2.0 Configuration/CLDC-1.1"->Seq("sonyericsson_v800_ver1_subr1s025sn"),
    "Vodafone/1.0/550SH/SHG001 Browser/UP.Browser/7.0.4.2 Profile/MIDP-2.0 Configuration/CLDC-1.1 Ext-V-Profile/VSCL-2.0.0"->Seq("sharp_550sh_ver1_sub7021"),
    "Vodafone/1.0/HTC_Mercury/1.20.321.6/Mozilla/4.0 (compatible; MSIE 4.01; Windows CE; MIDP-2.0 Configuration/CLDC-1.1; PPC; 240x320)"->Seq("vodafone_qtek_9100"),
    "Vodafone/1.0/V702NK/NKJ001/IMEI/SN354350000005026 Series60/2.6 Nokia6630/2.39.126 Profile/MIDP-2.0 Configuration/CLDC-1.1"->Seq("nokia_6630_ver1voda239126imei"),
    "SmarTone-Vodafone/SharpSX833/SHS001/1.0 Browser/UP.Browser/7.0.2.4 (GUI) Profile/MIDP-2.0 Configuration/CLDC-1.1 Ext-J-Profile/JSCL-1.2.2 Ext-V-Profile/VSCL-2.0.0"->Seq("sharp_sx833_ver1_subshs001107021a474"),
    "Vodafone/1.0/V702NK/NKJ001/IMEI/SN543500000053026 Series60/2.6 Nokia6630/2.39.236 Profile/MIDP-2.0 Configuration/CLDC-1"->Seq("nokia_6630_ver1voda239126imei"),

    // FIXME It fails with dummy due to Trie nature
    // "PippoBaudoInCariola"->Seq("generic"),

    ""->Seq("generic")
  )

//  # Sendo
//  SendoS330/9.A Browser/UP.Browser/7.0.2.1a.f.1.104 (GUI) Profile/MIDP-2.0 UP.Browser/7.0.2.3.119 (GUI) MMP/2.0=sendos330_ver1_sub14ag03
//
//  # Sanyo
//  Sanyo-C304SA/1.0 UP/4.1.19 UP.Browser/4.1.19-XXXX=sanyo_c304sa_ver1_sub4118xxxx
//  SANYO-S103/0.501 Profile/MIDP-2.0 Configuration/CLDC-1.1 UP.Browser/7.0.2.2.sn.1.168 (GUI) MMP/2.0=sanyo_s103_ver1_sub20
//
//  # BenQ
//  BENQ-Athena/0.1 Profile/MIDP-1.0 Configuration/CLDC-1.0 UP.Browser/6.1.2.4 (GUI) MMP/1.0=benq_athena_ver1_sub10
//  BenQ-A500/1.00/WAP2.0/MIDP1.0/CLDC1.0 UP.Browser/6.1.0.7.8.c.1.112 (GUI) MMP/1.0=benq_a500_ver1_sub61078c1103
//
//  # Pantech
//  PT-GF200 CLDC/CLDC-1.0 MIDP/MIDP-1.0 rigamonti=pantech_gf200j_ver1
//  PT-GF500/R02 Profile/MIDP-2.0 Configuration/CLDC-1.1=pt_gf500_ver1_subpgr01
//  Pantech c816 DK.02.04 UP.Browser/4.1.26l=pantech_c816_ver1_sub4126
//
//  # Toshiba
//  Toshiba TS608_TS30/v1.0 UP.Browser/6.2.4.9.d.2 (GUI) MMP/2.0=toshiba_ts608_ver1_subv106239d1
//
//  # Grundig
//  GRUNDIG M131/85108709 Browser/2.2.1 Profile/MIDP-1.0 Configuration/CLDC-1.0=grundig_m131_ver1_sub85108709
//  GRUNDIG M131/95108709 Browser/2.2.1 Profile/MIDP-1.0 Configuration/CLDC-1.0=grundig_m131_ver1_sub85108709
//  Grundig GR660/2.22.5.103 Mozilla/4.0 Profile/MIDP-2.0 Configuration/CLDC-1.1 (compatible; MSIE 4.01; Windows CE; PPC; 240x320)=grundig_gr660_sub2225102402011401
//
//  # HTC
//  HTC-8100/1.6 Mozilla/4.0 (compatible; MSIE 6.0; Windows CE; PPC; 240x320)=htc_8100_ver1_1
//  HTCArtist/212153 Mozilla/4.0 (compatible; MSIE 4.01; Windows CE; PPC; 240x320)=htc_artist_ver1_sub111204
//
//  #Windows Mobile OS
//  AUDIOVOX-SMT5600/1.3 Mozilla/4.0 (compatible; MSIE 4.01; Windows CE; Smartphone; 176x220)=audiovox_smt5600_ver1_sub176220_12
//
//
//  # portalmmm
//  #nothing= only exact matches here
//    portalmmm/2.0 v3xximode=mot_v3xxi_ver1
//  portalmmm/2.0 v3xximode(c30)=generic
//
//  #catch-all
//  YAS-COSMOS/2.0 UP.Browser/6.1.0.8.5 (GUI) MMP/1.0=yas_cosmos_ver1_sub61073
//  HAIER-V100 ObigoInternetBrowser/3.0=haier_v100_ver1
//  HAIER-V300 ObigoInternetBrowser/3.0=generic
//  TSM-7/53118323 Browser/1.2.1 Profile/MIDP-1.0 Configuration/CLDC-1.0=portalmmm_ver2_subtsm7_FFFFFFFF
//  CDM-8900TM/6.2 UP.Browser/6.2.2.4.d.1.103 (GUI) MMP/2.0=cdm_8900tm_ver1_sub6223d1103
//
//  #######################
//  # Recovery Heuristics
//    #######################
//
//  Nonsense
//  #Apple
//  Apple iPhone v2.1.4 CoreMedia=apple_iphone_ver1
//  Mozilla/5.0 (iPad Simulator; U; CPU iPhone OS 3_2 like Mac OS X; HW i386; en_us) AppleWebKit/525.18.1 (KHTML, like Gecko)=apple_ipad_ver1
//
//  #Openwave
//  PIPPOVOX-CDM9100/05.14 UP.Browser/4.1.21c=uptext_generic
//  PIPPOLESO-8450 UP.Browser/5.0.2.2 (GUI) MMP/2.0=upgui_generic
//  SHERLOCK-q8450 UP.Browser/6.0.2.2 (GUI) MMP/2.0=opwv_v6_generic
//  WATSON-q8450 UP.Browser/6.1.2.2 (GUI) MMP/2.0=opwv_v6_generic
//  MOUSE-845w0 UP.Browser/6.2.2.2 (GUI) MMP/2.0=opwv_v62_generic
//  DONALD-8e450 UP.Browser/7.0.2.2 (GUI) MMP/2.0=opwv_v7_generic
//  UNCLE/SCRUDGE-8450 UP.Browser/7.2.2.2 (GUI) MMP/2.0=opwv_v72_generic
//
//  #netfront
//  Mozilla/4.0 NetFront/3.0 Avantg/1.0=generic_netfront_ver3
//  Mozilla/4.0 (Phone;) NetFront/3.1 Avantg/1.0=generic_netfront_ver3_1
//  Mozilla/4.0 (Phone;) NetFront/3.2 Avantg/1.0=generic_netfront_ver3_2
//  Mozilla/4.0  NetFront/3.3 Avantg/1.0=generic_netfront_ver3_3
//  Mozilla/4.0  NetFront/3.4 Avantg/1.0=generic_netfront_ver3_4
//  Mozilla/4.0  NetFront/3.5 Avantg/1.0=generic_netfront_ver3_5
//  Mozilla/4.0  NetFront/4.0 Avantg/1.0=generic_netfront_ver4
//  HuaweiU7520/B000 Browser/NetFront/4.1 MMS/Obigo-MMS/Q05A SyncML/HW-SyncML/1.0 Java/HWJa/2.0 Profile/MIDP-2.0 Configuration/CLDC-1.1 Player/QTV-Player/5.=generic_netfront_ver4_1
//
//  #Nokia
//  NokiaJ341/2.0 (1.0450.0) SymbianOS/7.0s Series60/2.1 Profile/MIDP-2.0 Configuration/CLDC-1.0=nokia_generic_series60
//  Mozilla/4.0 (compatible; MSIE 5.0; Series80/2.0 Nokia2344/4.11.0 Profile/MIDP-2.0 Configuration/CLDC-1.0)=nokia_generic_series80
//  #Mozilla/4.0 (compatible; MSIE 5.0; Nokia2344/4.11.0 Profile/MIDP-2.0 Configuration/CLDC-1.0)=generic_xhtml
//
//  #Motorola
//  MOT-J34/17.213.7I MIB/2.2 Profile/MIDP-1.0 Configuration/CLDC-1.0=mot_mib22_generic
//  MOT-T51/17.213.7I MIB/BER2.2 Profile/MIDP-1.0 Configuration/CLDC-1.0=mot_mib22_generic
//
//  #Obigo/teleca
//  #Samsung-SDS345 AU-MIC/2.0 MMP/2.0 Profile/MIDP-2.0 Configuration/CLDC-1.1=generic_xhtml
//  Alcatel-DF-224/1.0 Profile/MIDP-2.0 Configuration/CLDC-1.1 ObigoInternetBrowser/Q03C=generic_xhtml
//  Alcatel-DF-224/1.0 Profile/MIDP-2.0 Configuration/CLDC-1.1 Obigo/Q03=generic_xhtml
//  Alcatel-DF-224/1.0 Profile/MIDP-2.0 Teleca Q03B1 Configuration/CLDC-1.1=generic_xhtml
//  Alcatel-DF-224/1.0 Profile/MIDP-2.0 Obigo/Q04 Configuration/CLDC-1.1=generic_xhtml
//  Alcatel-DF-224/1.0 ObigoInternetBrowser/2 Profile/MIDP-2.0 Configuration/CLDC-1.1=generic_xhtml
//  Alcatel-DF-224/1.0 Profile/MIDP-2.0 AU-OBIGO/2.2 Configuration/CLDC-1.1=generic_xhtml
//  LGE-DF-550/1.0 AU-MIC-LX550/2.0 Profile/MIDP-2.0 Configuration/CLDC-1.1=generic_xhtml
//
//  #BlackBerry
//  BlackBerry1238/2.0 A.23.2 Mozilla/2.0 (compatible; Go.Web/6.5; HandHTTP 1.1; Elaine/1.0; RIM957 )=blackberry_generic_ver2
//  BlackBerry2430/4.2.1 Profile/MIDP-2.0 Configuration/CLDC-1.1 VendorID/134=blackberry_generic_ver4_sub20
//  BlackBerry3244/3.2.1 Profile/MIDP-2.0 Configuration/CLDC-1.1=blackberry_generic_ver3_sub2
//  BlackBerry3244/3.5.1 Profile/MIDP-2.0 Configuration/CLDC-1.1=blackberry_generic_ver3_sub50
//  BlackBerry3244/3.7.1 Profile/MIDP-2.0 Configuration/CLDC-1.1=blackberry_generic_ver3_sub70
//
//  #Windows CE
//    Mozilla/2.0 (compatible; MSIE 3.02; Windows CE; Smartphone; )=generic_ms_mobile_browser_ver1
//  Mozilla/2.0 (compatible; MSIE 3.02; Windows CE; Smartphone; Mastella; Capaloni;)=generic_ms_mobile_browser_ver1
//  HTC_Cepaloni-Mastella/2.0 (grillo-compatible; MSIE 3.02; Windows CE; Smartphone; )=generic_ms_mobile_browser_ver1
//
//  #Opera Mini
//    PippoCustomizzato/8.01 (J2ME/MIDP; Opera Mini/1.2.2960; en; U; ssr)=browser_opera_mini_release1
//  PippoCustomizzato/8.01 (J2ME/MIDP; Opera Mini/2.2.2960; en; U; ssr)=browser_opera_mini_release2
//
//  #DoCoMo
//  DoCoMo/dal/sol/lEvaNTe=docomo_generic_jap_ver1
//  DoCoMo/2.0=docomo_generic_jap_ver2
//
//  #KDDI
//  KDDI/dal/sol/lEvaNTe=opwv_v62_generic
//  Mozilla/4.0 (compatible; MSIE 6.0; KDDI-SA39) Opera 8.60 [ja]=opera
//
//  #SPV
//  Mozilla/4.0 (compatible; MSIE 4.01; Windows CE; PPC; 240x320; SPV M3000; OpVer 12.3.2.105)=spv_m3000_ver1_subppc1232105
//  Mozilla/4.0 (compatible; MSIE 4.01; Windows CE; PPC; 240x320; SPV M3000; OpVer 12.3.2.104)=spv_m3000_ver1_subppc1232104
//  Mozilla/4.0 (compatible; MSIE 4.01; Windows CE; PPC; 240x320; SPV M3000; OpVer 12.3.99.99)=spv_m3000_ver1_subppc1232104
//
//  #SPV patch
//    Mozilla/4.0 (compatible; MSIE 4.01; Windows CE; PPC; 240x320; SPV M3000; OpVer 12.27.10.101)=spv_m3000_ver1_subov122710101
//  Mozilla/4.0 (compatible; MSIE 4.01; Windows CE; PPC; 240x320; SPV M3000; OpVer 12.27.10.999)=spv_m3000_ver1_subov122710101
//  Mozilla/4.0 (compatible; MSIE 4.01; Windows CE; PPC; 240x320; SPV M3000; OpVer 12.99.99.999)=spv_m3000_ver1_subov12
//
//  #Catch-all recovery
//    Mozilla/3.0 PDA Profile/MIDP-2.0 Configuration/CLDC-1.1=generic_xhtml
//
//  #Dummy user-agent
//  PippoBaudoInCariola=generic
//
//  #Web Browsers
//  #Opera
//  Opera/9.70 (Linux ppc64 ; U; en) Presto/2.2.1=opera_9
//  Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; sv) Opera 8.51=opera_8
//  Mozilla/4.0 (compatible; MSIE 6.0; MSIE 5.5; Windows NT 5.1) Opera 7.01 [de]=opera_7
//
//  #Andorid
//  Mozilla/5.0 (Linux; U; Android 0.5; en-us) AppleWebKit/522+ (KHTML, like Gecko) Safari/419.3=generic_android
//
//  # Web patch
//    Mozilla/5.0 (SymbianOS/9.3; U; Series60/3.2 NokiaN85-1/1.00 Profile/MIDP-2.0 Configuration/CLDC-1.1) AppleWebKit/413 (KHTML, like Gecko) Safari/413=nokia_n85_ver1
//  Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-GB; rv:1.9.0.7) Gecko/2009021906 Firefox/3.0.7=firefox_3
//  Mozilla/5.0 (Windows; U; Windows NT 5.1; pl; rv:1.9.0.7) Gecko/2009021910 Firefox/3.0.7 FirePHP/0.2.4=firefox_3
//  Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; i-NavFourF; .NET CLR 2.0.50727; .NET CLR 1.1.4322)=msie6_nt
//
//  Mozilla/5.0 (Windows; U; Windows NT 5.1; en-GB; rv:1.9.2) Gecko/20100115 Firefox/3.6 (.NET CLR 3.5.30729)=firefox_3
//
//
//  # Ticket #49 Identified as mobile browser
//  Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)=msie_8
//
//  # Ticket #50 Before Fix Identified as nokia_n900_ver1_subua /goodaccess_ver1_submsiepocketpc
//    Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)=msie6_nt
//  Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; InfoPath.2)=msie6_nt
//
//  # Before Fix Identified as tranxcode_mercury_proxy
//  Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30)=msie_7
//  Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_6; en-us) AppleWebKit/525.27.1 (KHTML, like Gecko) Version/3.2.1 Safari/525.27.1=safari_525_mac_osx
//  Mozilla/4.0 (compatible; MSIE 4.01; Windows CE; PPC; 240x320; SPV M700; OpVer 19.123.2.733)=ms_mobile_browser_ver1_subspvm700
//
//
//  #Ticket #57 Before Fix Identifed as samsung_sgh_t939_ver1(How to handle this Modify HTC Handler
//  Mozilla/5.0 (Linux; U; Android 1.5; ru-ru; HTC Hero Build/CUPCAKE) AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2 Mobile Safari/525.20.1=generic_android
//
//
//  #Ticket #65
//  Opera/9.80=opera_9
//  Opera/9.80 (Windows NT 5.1; U; en) Presto/2.5.22 Version/10.51=opera_9
//
//  # Malcolm Box 5/07/2010 wmlprogramming  identified as generic_web_browser
//  Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727)=msie6_nt
//
//  # SoftBank
//  # SoftBank/1.0/705NK/NKJ001/SN353641011127051 Series60/3.0 NokiaN73/3.0650 Profile/MIDP-2.0 Configuration/CLDC-1.1=nokia_
//  Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0;480*800;POLARIS 6.201;em1.0;WIFI;lgtelecom; EB10-20100804-178697799;LG-LU9400;0)=generic_lguplus_rexos_facebook_browser
//  Mozilla/4.0 (compatible;MSIE 7.0;Windows NT 5.2;480*800;WV02.00.01;;lgtelecom;EB10-20100621-717721593;LG-LU9400;0)=generic_lguplus_rexos_webviewer_browser
//  Mozilla/4.0 (compatible; MSIE 6.0; Windows CE);480*800;POLARIS 6.100;em1.0;lgtelecom=generic_lguplus_winmo_facebook_browser
//  Mozilla/4.0(compatible;MSIE 7.0;Windows NT 5.2;480*800;WV02.00.01;;lgtelecom;EB10-20101006-720032348;SHW-M7350;0)=generic_lguplus_rexos_webviewer_browser
//  Mozilla/4.0(compatible;MSIE 7.0;Windows NT 5.2;320*480;WV02.00.01;;lgtelecom;EB10-20100819-719028161;SHW-M130L;0)=generic_lguplus_rexos_webviewer_browser
//  Mozilla/5.0 (Linux; U; Android 2.2; ko-kr; LG-LU3000 Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1;LGUPLUS;01.00.00;WIFI; EB10-20100804-178697799;0=generic_lguplus_android_webkit_browser

}