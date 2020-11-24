/*
 * Copyright (c) 2007 Thomas Weise for sigoa
 * Simple Interface for Global Optimization Algorithms
 * http://www.sigoa.org/
 *
 * E-Mail           : info@sigoa.org
 * Creation Date    : 2007-03-05
 * Creator          : Thomas Weise
 * Original Filename: org.sfc.math.Mathematics.java
 * Last modification: 2007-03-05
 *                by: Thomas Weise
 *
 * License          : GNU LESSER GENERAL PUBLIC LICENSE
 *                    Version 2.1, February 1999
 *                    You should have received a copy of this license along
 *                    with this library; if not, write to theFree Software
 *                    Foundation, Inc. 51 Franklin Street, Fifth Floor,
 *                    Boston, MA 02110-1301, USA or download the license
 *                    under http://www.gnu.org/licenses/lgpl.html or
 *                    http://www.gnu.org/copyleft/lesser.html.
 *
 * Warranty         : This software is provided "as is" without any
 *                    warranty; without even the implied warranty of
 *                    merchantability or fitness for a particular purpose.
 *                    See the Gnu Lesser General Public License for more
 *                    details.
 */

package org.sfc.math;

import org.sfc.utils.ErrorUtils;

/**
 * This class contains some mathematic utilities.
 * 
 * @author Thomas Weise
 */
public final class Mathematics extends ConstantSet {
  // mathematical constants

  /**
   * sqrt 2
   */
  public static final double SQRT_2 = Math.sqrt(2d);

  /**
   * sqrt 3
   */
  public static final double SQRT_3 = Math.sqrt(3d);

  /**
   * sqrt 5
   */
  public static final double SQRT_5 = Math.sqrt(5d);

  /**
   * e ^ pi
   */
  public static final double E_PI = Math.pow(Math.E, Math.PI);

  /**
   * pi ^ e
   */
  public static final double PI_E = Math.pow(Math.PI, Math.E);

  /**
   * The natural logarithm of 10
   */
  public static final double LN_10 = Math.log(10.0d);

  /**
   * The natural logarithm of 2
   */
  public static final double LN_2 = Math.log(2.0d);

  /**
   * Euler-Mascheroni constant
   */
  public static final double EULER_MASCHERONI = 0.57721566490153286060651209008240243d;

  /**
   * Golden Ratio
   */
  public static final double GOLDEN_RATIO = 1.61803398874989484820458683436563811d;

  /**
   * Plastic Constant
   */
  public static final double PLASTIC_CONSTANT = 1.3247179572447460259609088544780973d;

  /**
   * Embree-Trefethen
   */
  public static final double EMBREE_TREFETHEN = 0.70258d;

  /**
   * Feigenbaum
   */
  public static final double FEIGENBAUM_DELTA = 4.66920160910299067185320382046620161d;

  /**
   * Feigenbaum
   */
  public static final double FEIGENBAUM_ALPHA = 2.50290787509589282228390287321821578d;

  /**
   * Twin Prime
   */
  public static final double TWIN_PRIME = 0.66016181584686957392781211001455577d;

  /**
   * Meissel-Mertens
   */
  public static final double MEISSEL_MERTENS = 0.26149721284764278375542683860869585d;

  /**
   * Bruns
   */
  public static final double BRUNS_TWIN_PRIMES = 1.9021605823d;

  /**
   * Bruns
   */
  public static final double BRUNS_QUADRUPELT_PRIMES = 0.8705883800d;

  /**
   * de Bruijn-Newman
   */
  public static final double DE_BRUIJN_NEWMAN = -2.7e-9d;

  /**
   * Catalan
   */
  public static final double CATALAN = 0.91596559417721901505460351493238411d;

  /**
   * Landau-Ramanujan
   */
  public static final double LANDAU_RAMANUJAN = 0.76422365358922066299069873125009232d;

  /**
   * Viswanath
   */
  public static final double VISWANATH = 1.13198824d;

  /**
   * Ramanujan-Soldner
   */
  public static final double RAMANUJAN_SOLDNER = 1.45136923488338105028396848589202744d;

  /**
   * Erdos-Borwein
   */
  public static final double ERDOS_BORWEIN = 1.60669515241529176378330152319092458d;

  /**
   * Bernstein
   */
  public static final double BERNSTEIN = 0.28016949902386913303d;

  /**
   * Gauss-Kuzwin
   */
  public static final double GAUSS_KUZWIN = 0.30366300289873265859744812190155623d;

  /**
   * Hafner-Sarnak-McCurley
   */
  public static final double HAFNER_SARNAK_MCCURLEY = 0.35323637185499598454d;

  /**
   * Golomb-Dickman
   */
  public static final double GOLOMB_DICKMAN = 0.62432998854355087099293638310083724d;

  /**
   * Cahen
   */
  public static final double CAHAN = 0.6434105463d;

  /**
   * Laplace Limit
   */
  public static final double LAPLACE_LIMIT = 0.66274341934918158097474209710925290d;

  /**
   * Alladi-Grinstead
   */
  public static final double ALLADI_GRINSTEAD = 0.8093940205d;

  /**
   * Lengyel
   */
  public static final double LENGYEL = 1.0986858055d;

  /**
   * Levy
   */
  public static final double LEVY = 3.27582291872181115978768188245384386d;

  /**
   * Apery
   */
  public static final double APERY = 1.20205690315959428539973816151144999d;

  /**
   * Mill
   */
  public static final double MILL = 1.30637788386308069046861449260260571d;

  /**
   * Backhouse
   */
  public static final double BACKHOUSE = 1.45607494858268967139959535111654356d;

  /**
   * Porter
   */
  public static final double PORTER = 1.4670780794d;

  /**
   * Lieb
   */
  public static final double LIEB = 1.5396007178d;

  /**
   * Niven
   */
  public static final double NIVEN = 1.70521114010536776428855145343450816d;

  /**
   * Sierpinski
   */
  public static final double SIERPINSKI = 2.58498175957925321706589358738317116d;

  /**
   * Khinchin
   */
  public static final double KHINCHIN = 2.68545200106530644530971483548179569d;

  /**
   * Fransen-Robinson
   */
  public static final double FRANSEN_ROBINSON = 2.80777024202851936522150118655777293d;

  /**
   * Parabolic
   */
  public static final double PARABOLIC_CONSTANT = 2.29558714939263807403429804918949039d;

  /**
   * Omega
   */
  public static final double OMEGA = 0.56714329040978387299996866221035555d;

  /**
   * the highest possible integer prime
   */
  public static final int MAX_PRIME = 2147483629;// getMaxPrime();

  /**
   * a threshold for computations
   */
  public static final double EPS = (10 * Double.MIN_VALUE);

  /**
   * The sqare root of the maximum integer.
   */
  private static final int SQRT_MAX_INT = ((int) (Math
      .sqrt(Integer.MAX_VALUE)));

  /**
   * The sqare root of the maximum integer threshold.
   */
  private static final int SQRT_MAX_INT_TH = (SQRT_MAX_INT * SQRT_MAX_INT);

  /**
   * Check whether <code>d</code> is a number and neither infinite nor
   * NaN.
   * 
   * @param d
   *          the double value to check
   * @return <code>true</code> if and only if <code>d</code> is a
   *         normal number,<code>false</code> otherwise
   */
  public static final boolean isNumber(final double d) {
    return (!(Double.isInfinite(d) || Double.isNaN(d)));
  }

  /**
   * Wrap a value so it fits into 0...mod-1
   * 
   * @param value
   *          the value to be wrapped
   * @param mod
   *          the modulo operator
   * @return the wrapped value
   */
  public static final int modulo(final int value, final int mod) {
    if (mod == 0) {
      if (value < 0)
        return Integer.MIN_VALUE;
      return Integer.MAX_VALUE;
    }
    if (value < 0)
      return (((value % mod) + mod) % mod);
    return (value % mod);
  }

  /**
   * Wrap a value so it fits into 0...mod-1
   * 
   * @param value
   *          the value to be wrapped
   * @param mod
   *          the modulo operator
   * @return the wrapped value
   */
  public static final long modulo(final long value, final long mod) {
    if (mod == 0l) {
      if (value < 0l)
        return Long.MIN_VALUE;
      return Long.MAX_VALUE;
    }
    if (value < 0)
      return (((value % mod) + mod) % mod);
    return (value % mod);
  }

  /**
   * Perform a division which returns a result rounded to the next higher
   * integer.
   * 
   * @param dividend
   *          the divident
   * @param divisor
   *          the divisor
   * @return the result
   */
  public static final int ceilDiv(final int dividend, final int divisor) {

    int r;

    if (divisor == 0)
      return ((dividend > 0) ? Integer.MAX_VALUE : Integer.MIN_VALUE);
    r = (dividend / divisor);

    if ((r * divisor) == dividend)
      return r;

    if (dividend < 0) {
      if (divisor < 0)
        return (r + 1);
      return (r - 1);
    }
    if (divisor < 0)
      return (r - 1);
    return (r + 1);
  }

  /**
   * Perform a division which returns a result rounded to the next higher
   * integer.
   * 
   * @param dividend
   *          the divident
   * @param divisor
   *          the divisor
   * @return the result
   */
  public static final long ceilDiv(final long dividend, final long divisor) {
    long r;

    if (divisor == 0)
      return ((dividend > 0) ? Long.MAX_VALUE : Long.MIN_VALUE);
    r = (dividend / divisor);

    if ((r * divisor) == dividend)
      return r;

    if (dividend < 0) {
      if (divisor < 0)
        return (r + 1);
      return (r - 1);
    }
    if (divisor < 0)
      return (r - 1);
    return (r + 1);
  }

  /**
   * check whether a given number is prime or not
   * 
   * @param num
   *          the number to check
   * @return <code>true</code> if it is, <code>false</code> otherwise
   */
  public static final boolean isPrime(final int num) {
    int i, m;

    if (num <= 1)
      return false;
    if (num <= 3)
      return true;

    if ((num & 1) == 0)
      return false;

    if (num > MAX_PRIME)
      return false;

    i = 3;

    if (num < SQRT_MAX_INT_TH) {
      do {
        if ((num % i) <= 0)
          return false;
        i += 2;
      } while ((i * i) < num);
    } else {
      m = SQRT_MAX_INT;
      do {
        if ((num % i) <= 0)
          return false;
        i += 2;
      } while (i <= m);
    }

    return true;
  }

  /**
   * Obtain the next prime greater than <code>num</code>.
   * 
   * @param num
   *          the number we want to find a greater or equal prime of
   * @return the greater prime
   */
  public static final int nextPrime(final int num) {
    int n, i, m;

    if (num >= MAX_PRIME)
      return MAX_PRIME;
    n = ((num < 3) ? 3 : (((num & 1) == 0) ? (num + 1) : num));

    main: for (;; n += 2) {
      i = 3;

      if (n < SQRT_MAX_INT_TH) {
        do {
          if ((n % i) <= 0)
            continue main;
          i += 2;
        } while ((i * i) < n);
      } else {
        m = SQRT_MAX_INT;
        do {
          if ((n % i) <= 0)
            continue main;
          i += 2;
        } while (i <= m);
      }

      return n;
    }
  }

  /**
   * Compare two doubles and return a number proportional to their
   * difference.
   * 
   * @param d1
   *          the first double
   * @param d2
   *          the second double
   * @return their comparison ratio, always a positive number...the larger,
   *         the more <code>d1</code> and <code>d2</code> differ
   */
  public static final double collate(final double d1, final double d2) {
    int c;

    c = Double.compare(d1, d2);
    if (c == 0)
      return 0d;

    if (Double.isNaN(d1)) {
      if (Double.isNaN(d2))
        return 0d;
      return Double.NaN;
    } else if (Double.isNaN(d2))
      return Double.NaN;

    if (Double.isInfinite(d1) || Double.isInfinite(d2)) {
      // return ((c < 0) ? Double.NEGATIVE_INFINITY
      // : Double.POSITIVE_INFINITY);
      return Double.POSITIVE_INFINITY;
    }

    if (d1 == 0d) {
      return Math.abs(d2);
    }
    if (d2 == 0d)
      return Math.abs(d1);

    return (Math.abs(d1 - d2) / Math.min(Math.abs(d1), Math.abs(d2)));
  }

  /**
   * Compare two doubles and return whether they are approximately equal.
   * 
   * @param d1
   *          the first double
   * @param d2
   *          the second double
   * @return their comparison ratio
   */
  public static final boolean approximatelyEqual(final double d1,
      final double d2) {
    return (collate(d1, d2) < EPS);
  }

  /**
   * Compute the logarithmus dualis of an (unsigned) integer value.
   * 
   * @param val
   *          the value
   * @return the logarithmus dualis of the (unsigned) integer
   *         <code>val</code>
   */
  public static final int ld(final int val) {
    int v, i;

    for (v = val, i = 0; v != 0; i++, v >>>= 1) {
      //
    }
    return i;
  }

  /**
   * the factorials
   */
  private static final long[] FACTORIALS = new long[] {//
  1l,// 0
      1l,// 1
      2l,// 2
      6l,// 3
      24l,// 4
      120l,// 5
      720l,// 6
      5040l,// 7
      40320l,// 8
      362880l,// 9
      3628800l,// 10
      39916800l,// 11
      479001600l,// 12
      6227020800l,// 13
      87178291200l,// 14
      1307674368000l,// 15
      20922789888000l,// 16
      355687428096000l,// 17
      6402373705728000l,// 18
      121645100408832000l,// 19
      2432902008176640000l,// 20
  };

  /**
   * compute the factorial of a number i
   * 
   * @param i
   *          the number
   * @return the factorial of <code>i</Coe
   */
  public static final long factorial(final int i) {
    if (i <= 1)
      return 1;
    if (i <= 20)
      return FACTORIALS[i];
    return Long.MAX_VALUE;
  }

  /**
   * the factorials
   */
  public static final double[] FACTORIALS2 = new double[] {//
  1.0d,//
      1.0d, // 1
      2.0d, // 2
      6.0d, // 3
      24.0d, // 4
      120.0d, // 5
      720.0d, // 6
      5040.0d, // 7
      40320.0d, // 8
      362880.0d, // 9
      3628800.0d, // 10
      3.99168E7d, // 11
      4.790016E8d, // 12
      6.2270208E9d, // 13
      8.71782912E10d, // 14
      1.307674368E12d, // 15
      2.0922789888E13d, // 16
      3.55687428096E14d, // 17
      6.402373705728E15d, // 18
      1.21645100408832E17d, // 19
      2.43290200817664E18d, // 20
      5.109094217170944E19d, // 21
      1.1240007277776077E21d, // 22
      2.585201673888498E22d, // 23
      6.204484017332394E23d, // 24
      1.5511210043330986E25d, // 25
      4.0329146112660565E26d, // 26
      1.0888869450418352E28d, // 27
      3.0488834461171384E29d, // 28
      8.841761993739701E30d, // 29
      2.6525285981219103E32d, // 30
      8.222838654177922E33d, // 31
      2.631308369336935E35d, // 32
      8.683317618811886E36d, // 33
      2.9523279903960412E38d, // 34
      1.0333147966386144E40d, // 35
      3.719933267899012E41d, // 36
      1.3763753091226343E43d, // 37
      5.23022617466601E44d, // 38
      2.0397882081197442E46d, // 39
      8.159152832478977E47d, // 40
      3.3452526613163803E49d, // 41
      1.4050061177528798E51d, // 42
      6.041526306337383E52d, // 43
      2.6582715747884485E54d, // 44
      1.1962222086548019E56d, // 45
      5.5026221598120885E57d, // 46
      2.5862324151116818E59d, // 47
      1.2413915592536073E61d, // 48
      6.082818640342675E62d, // 49
      3.0414093201713376E64d, // 50
      1.5511187532873822E66d, // 51
      8.065817517094388E67d, // 52
      4.2748832840600255E69d, // 53
      2.308436973392414E71d, // 54
      1.2696403353658276E73d, // 55
      7.109985878048635E74d, // 56
      4.052691950487722E76d, // 57
      2.350561331282879E78d, // 58
      1.3868311854568986E80d, // 59
      8.320987112741392E81d, // 60
      5.075802138772248E83d, // 61
      3.146997326038794E85d, // 62
      1.98260831540444E87d, // 63
      1.2688693218588417E89d, // 64
      8.247650592082472E90d, // 65
      5.443449390774431E92d, // 66
      3.647111091818868E94d, // 67
      2.4800355424368305E96d, // 68
      1.711224524281413E98d, // 69
      1.197857166996989E100d, // 70
      8.504785885678622E101d, // 71
      6.123445837688608E103d, // 72
      4.4701154615126834E105d, // 73
      3.3078854415193856E107d, // 74
      2.480914081139539E109d, // 75
      1.8854947016660498E111d, // 76
      1.4518309202828584E113d, // 77
      1.1324281178206295E115d, // 78
      8.946182130782973E116d, // 79
      7.156945704626378E118d, // 80
      5.797126020747366E120d, // 81
      4.75364333701284E122d, // 82
      3.945523969720657E124d, // 83
      3.314240134565352E126d, // 84
      2.8171041143805494E128d, // 85
      2.4227095383672724E130d, // 86
      2.107757298379527E132d, // 87
      1.8548264225739836E134d, // 88
      1.6507955160908452E136d, // 89
      1.4857159644817607E138d, // 90
      1.3520015276784023E140d, // 91
      1.24384140546413E142d, // 92
      1.1567725070816409E144d, // 93
      1.0873661566567424E146d, // 94
      1.0329978488239052E148d, // 95
      9.916779348709491E149d, // 96
      9.619275968248206E151d, // 97
      9.426890448883242E153d, // 98
      9.33262154439441E155d, // 99
      9.33262154439441E157d, // 100
      9.425947759838354E159d, // 101
      9.614466715035121E161d, // 102
      9.902900716486175E163d, // 103
      1.0299016745145622E166d, // 104
      1.0813967582402903E168d, // 105
      1.1462805637347078E170d, // 106
      1.2265202031961373E172d, // 107
      1.3246418194518284E174d, // 108
      1.4438595832024928E176d, // 109
      1.5882455415227421E178d, // 110
      1.7629525510902437E180d, // 111
      1.9745068572210728E182d, // 112
      2.2311927486598123E184d, // 113
      2.543559733472186E186d, // 114
      2.925093693493014E188d, // 115
      3.3931086844518965E190d, // 116
      3.969937160808719E192d, // 117
      4.6845258497542883E194d, // 118
      5.574585761207603E196d, // 119
      6.689502913449124E198d, // 120
      8.09429852527344E200d, // 121
      9.875044200833598E202d, // 122
      1.2146304367025325E205d, // 123
      1.5061417415111404E207d, // 124
      1.8826771768889254E209d, // 125
      2.372173242880046E211d, // 126
      3.012660018457658E213d, // 127
      3.8562048236258025E215d, // 128
      4.9745042224772855E217d, // 129
      6.466855489220472E219d, // 130
      8.471580690878817E221d, // 131
      1.118248651196004E224d, // 132
      1.4872707060906852E226d, // 133
      1.992942746161518E228d, // 134
      2.6904727073180495E230d, // 135
      3.659042881952547E232d, // 136
      5.01288874827499E234d, // 137
      6.917786472619486E236d, // 138
      9.615723196941086E238d, // 139
      1.346201247571752E241d, // 140
      1.89814375907617E243d, // 141
      2.6953641378881614E245d, // 142
      3.8543707171800706E247d, // 143
      5.550293832739301E249d, // 144
      8.047926057471987E251d, // 145
      1.17499720439091E254d, // 146
      1.7272458904546376E256d, // 147
      2.5563239178728637E258d, // 148
      3.808922637630567E260d, // 149
      5.7133839564458505E262d, // 150
      8.627209774233235E264d, // 151
      1.3113358856834518E267d, // 152
      2.006343905095681E269d, // 153
      3.089769613847349E271d, // 154
      4.789142901463391E273d, // 155
      7.47106292628289E275d, // 156
      1.1729568794264138E278d, // 157
      1.8532718694937338E280d, // 158
      2.946702272495037E282d, // 159
      4.714723635992059E284d, // 160
      7.590705053947215E286d, // 161
      1.2296942187394488E289d, // 162
      2.0044015765453015E291d, // 163
      3.2872185855342945E293d, // 164
      5.423910666131586E295d, // 165
      9.003691705778433E297d, // 166
      1.5036165148649983E300d, // 167
      2.526075744973197E302d, // 168
      4.2690680090047027E304d, // 169
      7.257415615307994E306d, // 170
  };

  /**
   * compute the factorial of a number i
   * 
   * @param i
   *          the number
   * @return the factorial of <code>i</Coe
   */
  public static final double factorial2(final int i) {
    if (i <= 1)
      return 1;
    if (i <= 170)
      return FACTORIALS2[i];
    return Double.POSITIVE_INFINITY;
  }

  /**
   * compute the binomial coefficient n over k
   * 
   * @param n
   *          n
   * @param k
   *          k
   * @return n over k
   */
  public static final long binomial(final int n, final int k) {
    int i;
    double s;

    if ((k < 0) || (k > n) || (n < 0))
      return 0;
    if (n <= 20)
      return ((FACTORIALS[n] / FACTORIALS[k]) / FACTORIALS[n - k]);

    s = 1;
    for (i = (n - k + 1); i <= n; i++) {
      s *= i;
    }
    for (i = k; i > 1; i--) {
      s /= i;
    }
    return (long) (0.5d + s);
  }

  /**
   * compute the binomial coefficient n over k
   * 
   * @param n
   *          n
   * @param k
   *          k
   * @return n over k
   */
  public static final double binomial2(final int n, final int k) {
    int i;
    double s;

    if ((k < 0) || (k > n) || (n < 0))
      return 0;
    if (n <= 20)
      return ((FACTORIALS[n] / FACTORIALS[k]) / FACTORIALS[n - k]);
    if (n <= 170)
      return ((FACTORIALS2[n] / FACTORIALS2[k]) / FACTORIALS2[n - k]);

    s = 1;
    for (i = (n - k + 1); i <= n; i++) {
      s *= i;
    }
    for (i = k; i > 1; i--) {
      s /= i;
    }
    return s;
  }

  /**
   * rounds r to decimals decimals
   * 
   * @param r
   *          The number to round.
   * @param decimals
   *          The count of decimals to round r to. If negative, maybe -1, r
   *          would be rounded to full decades.
   * @return r rounded to decimals decimals.
   */
  public static final double round(double r, int decimals) {
    if (r == 0)
      return r;

    double z = Math.rint(Math.log10(r));

    if (z < 0)
      z = decimals - z - 1;
    else
      z = decimals;

    if (z < 0.0)
      z = 1.0 / Math.rint(Math.pow(Math.rint(-z), 10d));
    else
      z = Math.rint(Math.pow(Math.rint(z), 10d));

    return Math.rint(r * z) / z;
  }

  /**
   * Ensure that a number is anything but zero.
   * 
   * @param val
   *          the value
   * @return a non-zero value.
   */
  public static final double notZero(final double val) {
    if (Math.abs(val) < EPS) {
      if (val < 0)
        return -EPS;
      return EPS;
    }
    return val;
  }

  /**
   * Obtain a list of all constants, including pi and e
   * 
   * @return the array of all mathematical constants
   */
  public static final double[] listConstants() {
    return listConstants(double.class, new String[] { "EPS" }); //$NON-NLS-1$
    // Field[] ff;
    // Field f;
    // int i, s, m;
    // double[] d;
    //
    //
    // ff = Mathematics.class.getDeclaredFields();
    // s = (ff.length - 1);
    // main: for (i = s; i >= 0; i--) {
    // f = ff[i];
    // if (f.getType() == double.class) {
    // m = f.getModifiers();
    // if (Modifier.isStatic(m) && Modifier.isFinal(m)) {
    // if (!("eps".equalsIgnoreCase(f.getName()))) { //$NON-NLS-1$
    // continue main;
    // }
    //
    // }
    // }
    // ff[i] = ff[--s];
    // }
    //
    // d = new double[s + 2];
    // d[s] = Math.E;
    // d[s + 1] = Math.PI;
    //
    // for (--s; s >= 0; s--) {
    // try {
    // d[s] = ff[s].getDouble(null);
    // } catch (Throwable t) {
    // d[s] = 0d;
    // }
    // }
    //
    // return d;
  }

  /**
   * the forbidden constructor
   */
  private Mathematics() {
    ErrorUtils.doNotCall();
  }
}
