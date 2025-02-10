public record TaxiServiceQualityAssessmentDto(
        Long id,

        @Size(min = 1, max = 255, message = "Aggregator name is required length 1-255")
        @NotEmpty(message = "Aggregator name is required")
        String aggregatorName,

        @Size(min = 1, max = 255, message = "Assessment date is required length 1-255")
        @NotEmpty(message = "Assessment date is required")
        String assessmentDate,

        String reportFile,

        String assessmentType,
        String assessmentTypeName,

        String assessmentStatus,
        String assessmentStatusName,

        List<PolicyDto> relatedPolicies
) {
}