var builder = WebApplication.CreateBuilder(args);

var port = Environment.GetEnvironmentVariable("PROGRAM_PORT") ?? "8080";

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

app.Run();
