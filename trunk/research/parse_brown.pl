#!/usr/bin/perl

while(<>) {
	chomp;
	
	my @tokens = split("[ \t]+", $_);
	next unless @tokens > 1;
	my @new_sentence;
	for my $token (@tokens) {
		my @word_tokens = split("/", $token);
		push @new_sentence, $word_tokens[0];
	}

	my $str = join(" ", @new_sentence);

	print "$str\n";
}
