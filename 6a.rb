# For each input number, count down to 0 then restart at 6 and add an 8 to the list. Do this (80) times, then return how long the list will be.
# Brute forced because it was fast enough, I know there's probably a clever mathematical function for this

input_file = "inputs/6.txt"
input = File.open(input_file, &:readline).split(",").map(&:to_i)

days = 80

days.times do |day|
  new_input = []
  input.each do |counter|
    if counter == 0
      new_input << 6
      new_input << 8
    else
      new_input << counter - 1
    end
  end
  input = new_input
end

p input.length
