/**
This is NOT recommended as it uses a weaker hash.

On the server use the matching java class:

	com.nimbusds.srp6.js.SRP6JavascriptServerSessionSHA1

*/

function SRP6JavascriptClientSessionSHA1(){ }

SRP6JavascriptClientSessionSHA1.prototype = new SRP6JavascriptClientSession();

SRP6JavascriptClientSessionSHA1.prototype.N = function() {
	return new BigInteger("11144252439149533417835749556168991736939157778924947037200268358613863350040339017097790259154750906072491181606044774215413467851989724116331597513345603", 10);
}

SRP6JavascriptClientSessionSHA1.prototype.g = function() {
	return new BigInteger(SRP6CryptoParams.g_base10, 10);
}

SRP6JavascriptClientSessionSHA1.prototype.H = function (x) {
		return CryptoJS.SHA1(x).toString().toLowerCase();
}

SRP6JavascriptClientSessionSHA1.prototype.k = new BigInteger(SRP6CryptoParams.k_base16, 16);
