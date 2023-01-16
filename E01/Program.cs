using Microsoft.AspNetCore.Mvc;

var PROJECT_AUTHOR = "MohammadSafari";

var builder = WebApplication.CreateBuilder(args);

var getOption = (string argName) => args.SkipWhile(arg => arg != argName).Skip(1).FirstOrDefault();
var portValidator = (string port) => port.All(char.IsDigit) ? port : throw new ArgumentException("Invalid port number: " + port);
var port = getOption("--program-port") ?? Environment.GetEnvironmentVariable("PROGRAM_PORT") ?? "8080";
portValidator(port);

// Add services to the container.
builder.Services.AddRazorPages();

var app = builder.Build();

app.Urls.Add($"http://*:{port}");

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");  
}
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.MapRazorPages();

app.MapGet("/hello", ([FromQuery(Name = "name")] string? name) => $"Hello {name ?? "Stranger"}");
app.MapGet("/author", () => $"Hello {PROJECT_AUTHOR}");

app.Run();
